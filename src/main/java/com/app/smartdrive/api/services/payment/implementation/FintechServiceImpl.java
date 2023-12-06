package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.catalina.mapper.Mapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Response.FintechDto;
import com.app.smartdrive.api.dto.payment.Response.FintechIdForUserDto;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.mapper.payment.FintechMapper;
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
    public Boolean updateFintech(Long fint_entityid,String fint_name, String fint_desc) {
        Optional<Fintech> fintechId = fintechRepository.findById(fint_entityid);
        List<BusinessEntity> businessData = businessRepository.findAll();
        Fintech fintech = fintechId.get();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        if(fintechId.isPresent()){
            for (BusinessEntity bisnis : businessData) {
                if(fint_entityid.equals(bisnis.getEntityId())){
                    if(fint_name!=null && fint_desc!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        fintech.setFint_name(fint_name);
                        fintech.setFint_desc(fint_desc);
                        businessRepository.save(bisnis);
                        fintechRepository.save(fintech);
                    }else if(fint_name!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        fintech.setFint_name(fint_name);
                        businessRepository.save(bisnis);
                        fintechRepository.save(fintech);
                    }else if(fint_desc!=null){
                        bisnis.setEntityModifiedDate(LocalDateTime.now());
                        fintech.setFint_desc(fint_desc);
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
    public FintechIdForUserDto getUserFintId(String fint_name) {
        Optional<Fintech> getName = fintechRepository.findByFintNameOptional(fint_name);
        List<Fintech> fintData = fintechRepository.findAll();

        return null;
    }


    @Override
    public List<FintechDto> getAll() {
        List<Fintech> listFintech = fintechRepository.findAll();
        List<FintechDto> listDto = new ArrayList<>();
        for (Fintech fint : listFintech) {
            FintechDto fintechDto = FintechMapper.convertEntityToDto(fint);
            listDto.add(fintechDto);
        }
        return listDto;
    }


    @Override
    public FintechDto getById(Long id) {
        Optional<Fintech> idFintech = fintechRepository.findById(id);
        FintechDto fintechDto = new FintechDto();
        if(idFintech.isPresent()){
            Fintech fint = idFintech.get();
            fintechDto = FintechMapper.convertEntityToDto(fint);
        }

        return fintechDto;
    }

    
    @Override
    public FintechDto save(FintechDto fintechDto) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        businessEntity = businessEntityService.save(businessEntity);

        Fintech fintech = new Fintech();
        fintech.setBusinessEntity(businessEntity);
        fintech.setFint_entityid(businessEntity.getEntityId());
        fintech.setFint_name(fintechDto.getFint_name());
        fintech.setFint_desc(fintechDto.getFint_desc());
        addFintech(fintech);

        FintechDto fintechDtoo = FintechMapper.convertEntityToDto(fintech);

        return fintechDtoo;
    }

    
    
    
    
    

    
}
