package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.PaymentOperasional.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Payment.PaymentDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.payment.PaymentOperasional.PaymentsMapper;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.payment.PaymentService;

import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Qualifier("bankServiceImpl")
public class BankServiceImpl implements PaymentService {
    private final EntityManager entityManager;
    private final BanksRepository banksRepository;
    private final BusinessEntityService businessEntityService;
    private final BusinessEntityRepository repositoryBisnis;

    public Banks addaBanks(Banks banks){
        entityManager.persist(banks);
        return banks;
    }

    private void checkBanks(String bankName){
        List<Banks> listBanks = banksRepository.findAll();
        for (Banks banks : listBanks) {
            if(bankName.equals(banks.getBankName())){
                throw new EntityNotFoundException(bankName + " Sudah terdaftar! Harap masukan nama bank lainya"); 
            }
        }
    }


    public Boolean deleteById(Long id) {
        Banks findId = banksRepository.findById(id).orElse(null);
        Optional<BusinessEntity> dataBussines = repositoryBisnis.findById(id);
        BusinessEntity business = dataBussines.get();

        if(findId == null){
            throw new UserNotFoundException(id + " Tidak terdaftar ");
        }else{
               if(checkBusinesEntity(id,repositoryBisnis)) {
                   banksRepository.deleteBanksByID(id);
                   repositoryBisnis.deleteById(business.getEntityId());
               }
        }
        return true;
    }

    @Override
    public PaymentDtoResponse addPayment(PaymentRequestsDto requests) {
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        BusinessEntity businessEntityId = businessEntityService.save(busines);

        Banks bankss = Banks.builder()
        .businessEntity(busines)
        .bankEntityid(businessEntityId.getEntityId())
        .bankName(requests.getPaymentName())
        .bankDesc(requests.getPaymentDesc())
        .build();
        
        String bankName = requests.getPaymentName();
        Banks banksName = banksRepository.findByBankNameOptional(bankName).orElse(null);
        PaymentDtoResponse dtoResponse = PaymentsMapper.convertEntityToDto(requests);
        if(banksName != null){
            throw new EntityNotFoundException(bankName + " Sudah Terdaftar");
        }
        addaBanks(bankss);
        return dtoResponse;
   
    }



    @Override
    public PaymentDtoResponse updateById(Long id, PaymentRequestsDto requests) {
        Banks banksData = banksRepository.findById(id).orElse(null);
        PaymentDtoResponse response = new PaymentDtoResponse();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        if(banksData == null){
            throw new EntityNotFoundException("Tidak terdapat " + id + " tersebut");
        }
            if(checkBusinesEntity(id, repositoryBisnis)){
                checkBanks(requests.getPaymentName());
                businessEntity.setEntityModifiedDate(LocalDateTime.now());
                banksData.setBankName(requests.getPaymentName());
                banksData.setBankDesc(requests.getPaymentDesc());
                repositoryBisnis.save(businessEntity);
                banksRepository.save(banksData);
            }
        
        response = PaymentsMapper.convertEntityToDto(requests);
        return response;
    }



    @Override
    public List<PaymentDtoResponse> getAll() {
        List<Banks> listBanks = banksRepository.findAll();

        List<PaymentDtoResponse> dtoResponse= new ArrayList<>();
        for (Banks banks: listBanks) {
            PaymentDtoResponse response = PaymentDtoResponse.builder()
                    .paymentEntityId(banks.getBankEntityid())
                    .paymentName(banks.getBankName())
                    .paymentDesc(banks.getBankDesc())
                    .build();

           dtoResponse.add(response);
        }
       
        return dtoResponse;
    }

    @Override
    public PaymentDtoResponse getById(Long id) {
        Banks idBanks = banksRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException("Tidak terdapat id: " + id));
        PaymentRequestsDto requestsDto = new PaymentRequestsDto();
        requestsDto.setPaymentName(idBanks.getBankName());
        requestsDto.setPaymentDesc(idBanks.getBankDesc());
        PaymentDtoResponse response = PaymentsMapper.convertEntityToDto(requestsDto);
        return response;
    }

    
    
}
