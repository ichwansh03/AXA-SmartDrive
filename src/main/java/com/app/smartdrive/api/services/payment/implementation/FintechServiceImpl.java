package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.catalina.mapper.Mapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Request.Fintech.FintechDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.payment.Fintech.FintechMapper;
import com.app.smartdrive.api.repositories.payment.FintechRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.payment.FintechService;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FintechServiceImpl implements FintechService {
    private final FintechRepository fintechRepository;
    private final EntityManager entityManager;
    private final BusinessEntityService businessEntityService;
    private final BusinessEntityRepository businessRepository;

    public Fintech addFintech(Fintech fintech){
        entityManager.persist(fintech);
        return fintech;
    }
   
    
   
    @Override
    public FintechDtoResponse addFintech(FintechDtoRequests fintechDtoRequests) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        Long businessEntityId = businessEntityService.save(businessEntity);

        Fintech fintech = new Fintech();
        fintech.setBusinessEntity(businessEntity);
        fintech.setFint_entityid(businessEntityId);
        fintech.setFint_name(fintechDtoRequests.getFint_name());
        fintech.setFint_desc(fintechDtoRequests.getFint_desc());
        addFintech(fintech);

        FintechDtoResponse dto = FintechMapper.convertEntityToDto(fintech);
        return dto;
    }



    @Override
    public Boolean deleteFintech(Long fintech_entityid) {
        Optional<Fintech> findId = fintechRepository.findById(fintech_entityid);
        List<BusinessEntity> businesData = businessRepository.findAll();
        if(findId.isPresent()){
            for (BusinessEntity bisnis: businesData) {
                if(fintech_entityid.equals(bisnis.getEntityId())){
                    businessRepository.deleteById(bisnis.getEntityId());
                    fintechRepository.deleteFintechById(fintech_entityid);
                }
            }
            return true;
        }
        return false;
        
    }


    @Override
    public Boolean updateFintech(Long fint_entityid,FintechDtoRequests requests) {
        Optional<Fintech> fintechId = fintechRepository.findById(fint_entityid);
        List<BusinessEntity> businessData = businessRepository.findAll();
        Fintech fintech = fintechId.get();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        if(fintechId.isPresent()){
            for (BusinessEntity bisnis : businessData) {
                if(fint_entityid.equals(bisnis.getEntityId())){
                    if(requests.getFint_name()!=null && requests.getFint_desc()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        fintech.setFint_name(requests.getFint_name());
                        fintech.setFint_desc(requests.getFint_desc());
                        businessRepository.save(bisnis);
                        fintechRepository.save(fintech);
                    }else if(requests.getFint_name()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        fintech.setFint_name(requests.getFint_name());
                        businessRepository.save(bisnis);
                        fintechRepository.save(fintech);
                    }else if(requests.getFint_desc()!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        fintech.setFint_desc(requests.getFint_desc());
                        businessRepository.save(bisnis);
                        fintechRepository.save(fintech);
                    }else{
                        return false;
                    }
                    return true;
                }
            }
        }return false;
        
    }

    


    @Override
    public FintechIdForUserDtoResponse getUserFintId(String fint_name) {
        Optional<Fintech> getName = fintechRepository.findByFintNameOptional(fint_name);
        FintechIdForUserDtoResponse dto = new FintechIdForUserDtoResponse();

        if(getName.isPresent()){
            Fintech fintech = getName.get();
            dto.setFint_entityid(fintech.getFint_entityid());
        }

        return dto;
    }


    @Override
    public List<FintechDtoResponse> getAll() {
        List<Fintech> listFintech = fintechRepository.findAll();
        List<FintechDtoResponse> listDto = new ArrayList<>();
        for (Fintech fint : listFintech) {
            FintechDtoResponse fintechDto = FintechMapper.convertEntityToDto(fint);
            listDto.add(fintechDto);
        }
        return listDto;
    }


    @Override
    public FintechDtoResponse getById(Long id) {
        Optional<Fintech> idFintech = fintechRepository.findById(id);
        FintechDtoResponse fintechDto = new FintechDtoResponse();
        if(idFintech.isPresent()){
            Fintech fint = idFintech.get();
            fintechDto = FintechMapper.convertEntityToDto(fint);
        }

        return fintechDto;
    }

    
    @Override
    public FintechDtoResponse save(FintechDtoResponse fintechDto) {

        return null;
    }

    
    
    
    
    

    
}
