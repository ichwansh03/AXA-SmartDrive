package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.Banks.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.mapper.payment.Banks.PaymentsMapper;
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
            if(bankName.equals(banks.getBank_name())){
                throw new EntityNotFoundException(bankName + " Sudah terdaftar! Harap masukan nama bank lainya"); 
            }
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        Banks findId = banksRepository.findById(id).orElse(null);
        List<BusinessEntity> businesData = repositoryBisnis.findAll();

        if(findId == null){
            throw new UserNotFoundException(id + " Tidak terdaftar ");
        }else{
                for (BusinessEntity bisnis: businesData) {  
                    if(id.equals(bisnis.getEntityId())){
                        banksRepository.deleteBanksByID(id);
                        repositoryBisnis.deleteById(bisnis.getEntityId());
                    }
                }
            return true;
        }
    }



    @Override
    public PaymentDtoResponse addPayment(PaymentRequestsDto requests) {
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        BusinessEntity businessEntityId = businessEntityService.save(busines);

        Banks bankss = Banks.builder()
        .businessEntity(busines)
        .bank_entityid(businessEntityId.getEntityId())
        .bank_name(requests.getPayment_name())
        .bank_desc(requests.getPayment_desc())
        .build();
        
        String bankName = requests.getPayment_name();
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
            if(CommonUtils.checkBusinesEntity(id, repositoryBisnis)){
                checkBanks(requests.getPayment_name());
                businessEntity.setEntityModifiedDate(LocalDateTime.now());
                banksData.setBank_name(requests.getPayment_name());
                banksData.setBank_desc(requests.getPayment_desc());
                repositoryBisnis.save(businessEntity);
                banksRepository.save(banksData);
            }
        
        response = PaymentsMapper.convertEntityToDto(requests);
        return response;
    }



    @Override
    public List<PaymentDtoResponse> getAll() {
        List<Banks> listBanks = banksRepository.findAll();
        List<PaymentRequestsDto> listDtoRequest = listBanks.stream()
                .map(banks -> {
                    PaymentRequestsDto dto = new PaymentRequestsDto();
                    dto.setPayment_name(banks.getBank_name());
                    dto.setPayment_desc(banks.getBank_desc());
                    return dto;
                })
                .collect(Collectors.toList());
        List<PaymentDtoResponse> dtoResponse = listDtoRequest.stream()
        .map(PaymentsMapper::convertEntityToDto).collect(Collectors.toList());
       
        return dtoResponse;
    }

    @Override
    public PaymentDtoResponse getById(Long id) {
        Banks idBanks = banksRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException("Tidak terdapat id: " + id));
        PaymentRequestsDto requestsDto = new PaymentRequestsDto();
        requestsDto.setPayment_name(idBanks.getBank_name());
        requestsDto.setPayment_desc(idBanks.getBank_desc());
        PaymentDtoResponse response = PaymentsMapper.convertEntityToDto(requestsDto);
        return response;
    }

    
    
}
