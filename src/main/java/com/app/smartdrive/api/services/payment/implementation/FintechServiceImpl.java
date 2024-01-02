package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.text.html.Option;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserExistException;
import com.app.smartdrive.api.Exceptions.UserNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.Banks.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Request.Fintech.FintechDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.mapper.payment.Banks.PaymentsMapper;
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
            if(fintName.equals(fintech.getFint_name())){
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
        .fint_entityid(businessEntityId.getEntityId())
        .fint_name(requests.getPayment_name())
        .fint_desc(requests.getPayment_desc())
        .build();

        String fintName = requests.getPayment_name();
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
            if(CommonUtils.checkBusinesEntity(id, repositoryBisnis)){
                checkFintech(requests.getPayment_name());
                businessEntity.setEntityModifiedDate(LocalDateTime.now());
                fintechData.setFint_name(requests.getPayment_name());
                fintechData.setFint_desc(requests.getPayment_desc());
                repositoryBisnis.save(businessEntity);
                fintechRepository.save(fintechData);
            }
        
        response = PaymentsMapper.convertEntityToDto(requests);
        return response;
    }

    @Override
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
        List<PaymentRequestsDto> listDtoRequest = listFintech.stream()
                .map(fintech -> {
                    PaymentRequestsDto dto = new PaymentRequestsDto();
                    dto.setPayment_name(fintech.getFint_name());
                    dto.setPayment_desc(fintech.getFint_desc());
                    return dto;
                })
                .collect(Collectors.toList());
        List<PaymentDtoResponse> dtoResponse = listDtoRequest.stream()
        .map(PaymentsMapper::convertEntityToDto).collect(Collectors.toList());
       
        return dtoResponse;
    }

    @Override
    public PaymentDtoResponse getById(Long id) {
        Fintech idFintech = fintechRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException("Tidak terdapat id: " + id));
        PaymentRequestsDto requestsDto = new PaymentRequestsDto();
        requestsDto.setPayment_name(idFintech.getFint_name());
        requestsDto.setPayment_desc(idFintech.getFint_desc());
        PaymentDtoResponse response = PaymentsMapper.convertEntityToDto(requestsDto);
        return response;
    }

   

    
   
}
