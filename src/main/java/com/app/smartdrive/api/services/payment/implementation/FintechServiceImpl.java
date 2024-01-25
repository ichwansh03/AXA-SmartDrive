package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.PaymentOperasional.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Payment.PaymentDtoResponse;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.payment.PaymentOperasional.PaymentsMapper;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.payment.PaymentService;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Qualifier("fintechServiceImpl")
@Transactional
public class FintechServiceImpl implements PaymentService {
    private final FintechRepository fintechRepository;
    private final EntityManager entityManager;
    private final BusinessEntityService businessEntityService;
    private final BusinessEntityRepository repositoryBisnis;
    private final PaymentService paymentService;

    public Fintech addedFintech(Fintech fintech){
        entityManager.persist(fintech);
        return fintech;
    }

    private void checkFintech(String fintName){
        List<Fintech> listFintech = fintechRepository.findAll();
        for (Fintech fintech : listFintech) {
            if(fintName.equals(fintech.getFintName())){
                throw new EntityNotFoundException(fintName + " Sudah terdaftar! Harap masukan nama bank lainya"); 
            }
        }
    }

    @Override
    public PaymentDtoResponse addPayment(PaymentRequestsDto requests) {
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        BusinessEntity businessEntityId = businessEntityService.save(busines);
        
        Fintech fintech = Fintech.builder()
        .businessEntity(busines)
        .fintEntityid(businessEntityId.getEntityId())
        .fintName(requests.getPaymentName())
        .fintDesc(requests.getPaymentDesc())
        .build();

        String fintName = requests.getPaymentName();
        Fintech fint = fintechRepository.findByFintNameOptional(fintName).orElse(null);
        PaymentDtoResponse dtoResponse = PaymentsMapper.convertEntityToDto(requests);
        if(fint != null){
            throw new EntityNotFoundException(fint + " Sudah Terdaftar");
        }
        addedFintech(fintech);
        
        return dtoResponse;
    }
  



    @Override
    public PaymentDtoResponse updateById(Long id, PaymentRequestsDto requests) {
        Fintech fintechData = fintechRepository.findById(id).orElse(null);
        PaymentDtoResponse response = new PaymentDtoResponse();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        if(fintechData == null){
            throw new EntityNotFoundException("Tidak terdapat " + id + " tersebut");
        }
            if(checkBusinesEntity(id, repositoryBisnis)){
                checkFintech(requests.getPaymentName());
                businessEntity.setEntityModifiedDate(LocalDateTime.now());
                fintechData.setFintName(requests.getPaymentName());
                fintechData.setFintDesc(requests.getPaymentDesc());
                repositoryBisnis.save(businessEntity);
                fintechRepository.save(fintechData);
            }
        
        response = PaymentsMapper.convertEntityToDto(requests);
        return response;
    }


    public Boolean deleteById(Long id) {
        Fintech findId = fintechRepository.findById(id).orElse(null);
        List<BusinessEntity> businesData = repositoryBisnis.findAll();

        if(findId == null){
            throw new UserNotFoundException(id + " Tidak terdaftar ");
        }else{
                for (BusinessEntity bisnis: businesData) {  
                    if(id.equals(bisnis.getEntityId())){
                        fintechRepository.deleteFintechById(id);
                        repositoryBisnis.deleteById(bisnis.getEntityId());
                    }
                }
            return true;
        }
    }

    @Override
    public List<PaymentDtoResponse> getAll() {
         List<Fintech> listFintech = fintechRepository.findAll();
        List<PaymentDtoResponse> listDtoResponse = new ArrayList<>();
        for (Fintech fint: listFintech) {
            PaymentDtoResponse dtoResponse = PaymentDtoResponse.builder()
                    .paymentEntityId(fint.getFintEntityid())
                    .paymentName(fint.getFintName())
                    .paymentDesc(fint.getFintDesc())
                    .build();
            listDtoResponse.add(dtoResponse);
        }


        return listDtoResponse;
    }

    @Override
    public PaymentDtoResponse getById(Long id) {
        Fintech idFintech = fintechRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException("Tidak terdapat id: " + id));
        PaymentRequestsDto requestsDto = new PaymentRequestsDto();
        requestsDto.setPaymentName(idFintech.getFintName());
        requestsDto.setPaymentDesc(idFintech.getFintDesc());
        PaymentDtoResponse response = PaymentsMapper.convertEntityToDto(requestsDto);
        return response;
    }

   

    
   
}
