package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.FintechDto;
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
public class FintechServiceImpl implements FintechService {
    private final FintechRepository fintechRepository;
    private final EntityManager entityManager;
    private final BusinessEntityService businessEntityService;
    private final BusinessEntityRepository businessRepository;

    @Transactional
    public Fintech addFintech(Fintech fintech){
        entityManager.persist(fintech);
        return fintech;
    }
    @Override
    public List<FintechDto> findAllFintech() {
        
        List<Fintech> listFintech = fintechRepository.findAll();
        List<FintechDto> listDto = new ArrayList<>();

        for (Fintech fintech : listFintech) {
            FintechDto fintechDto = FintechMapper.convertEntityToDto(fintech);
            listDto.add(fintechDto);
        }
        return listDto;
    }


    @Override
    public List<FintechDto> findFintechById(Long fintech_entityid) {
        Optional<Fintech> fintechId = fintechRepository.findById(fintech_entityid);
        List<Fintech> fintechData = fintechRepository.findAll();
        List<FintechDto> listDto = new ArrayList<>();
        if(fintechId.isPresent()){
            for (Fintech fi : fintechData) {
                if(fintech_entityid.equals(fi.getFint_entityid())){
                    FintechDto fintDto = FintechMapper.convertEntityToDto(fi);
                    listDto.add(fintDto);
                }
            }
        }
        return listDto;
    }

    @Transactional
    @Override
    public FintechDto addFintech(FintechDto fintechDto) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        Long businessEntityId = businessEntityService.save(businessEntity);

        Fintech fintech = new Fintech();
        fintech.setBusinessEntity(businessEntity);
        fintech.setFint_entityid(businessEntityId);
        fintech.setFint_name(fintechDto.getFint_name());
        fintech.setFint_desc(fintechDto.getFint_desc());
        addFintech(fintech);

        FintechDto fintechDtoo = FintechMapper.convertEntityToDto(fintech);
        return fintechDtoo;
    }

    @Transactional
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

    @Transactional
    @Override
    public Boolean updateFintech(Long fint_entityid,FintechDto fintechDto) {
        Optional<Fintech> fintechId = fintechRepository.findById(fint_entityid);
        List<BusinessEntity> businessData = businessRepository.findAll();
        Fintech fintech = fintechId.get();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        List<FintechDto> listDto = new ArrayList<>();
        if(fintechId.isPresent()){
            for (BusinessEntity bisnis : businessData) {
                if(fint_entityid.equals(bisnis.getEntityId())){
                    bisnis.setEntityModifiedDate(LocalDateTime.now());
                    fintech.setFint_name(fintechDto.getFint_name());
                    fintech.setFint_desc(fintechDto.getFint_desc());
                    businessRepository.save(bisnis);
                    fintechRepository.save(fintech);
                    FintechDto dto = FintechMapper.convertEntityToDto(fintech);
                    listDto.add(dto);
                }
            }
            return true;
        }else{
            return false;
        }
        
    }


    @Override
    public List<Fintech> getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Fintech getById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Fintech save(Fintech entity) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    

    
}
