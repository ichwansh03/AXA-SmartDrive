package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.catalina.mapper.Mapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserExistException;
import com.app.smartdrive.api.Exceptions.UserNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
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
    public FintechDtoResponse addFintech(FintechDtoRequests fintechDtoRequests){
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());
        BusinessEntity businessEntityId = businessEntityService.save(businessEntity);

        Fintech fintech = new Fintech();
        fintech.setBusinessEntity(businessEntity);
        fintech.setFint_entityid(businessEntityId.getEntityId());
        fintech.setFint_name(fintechDtoRequests.getFint_name());
        fintech.setFint_desc(fintechDtoRequests.getFint_desc());

        FintechDtoResponse dto = FintechMapper.convertEntityToDto(fintech);

        String fintName = fintechDtoRequests.getFint_name();
        Fintech existingFintech = fintechRepository.findByFintNameOptional(fintName).orElse(null);
        if(existingFintech == null){
            addFintech(fintech);
            return dto;
        }else{
            throw new UserExistException(fintName + " Sudah terdaftar ");
        }
    }

    @Override
    public Boolean deleteFintech(Long fintech_entityid) {
        Fintech findId = fintechRepository.findById(fintech_entityid).orElse(null);
        List<BusinessEntity> businesData = businessRepository.findAll();

        if(findId == null){
            throw new UserNotFoundException(fintech_entityid + " Tidak terdaftar ");
        }else{
                for (BusinessEntity bisnis: businesData) {  
                    if(fintech_entityid.equals(bisnis.getEntityId())){
                        businessRepository.deleteById(bisnis.getEntityId());
                        fintechRepository.deleteFintechById(fintech_entityid);
                    }
                }
            return true;
        }
    }

    @Override
    public Boolean updateFintech(Long fint_entityid,FintechDtoRequests requests) {
        Fintech fintechData = fintechRepository.findById(fint_entityid).orElse(null);
        List<BusinessEntity> businessData = businessRepository.findAll();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        if(fintechData == null){
            throw new UserNotFoundException("Terjadi Kesalahan Saat Update Fintech");
        }else{
            for (BusinessEntity bisnis : businessData) {
                if(fint_entityid.equals(bisnis.getEntityId())){
                    bisnis.setEntityModifiedDate(LocalDateTime.now());
                    fintechData.setFint_name(requests.getFint_name());
                    fintechData.setFint_desc(requests.getFint_desc());
                    businessRepository.save(bisnis);
                    fintechRepository.save(fintechData);
                }
            }
            return true;
        } 
    }
    
    @Override
    public FintechIdForUserDtoResponse getUserFintId(String fint_name) {
        String nameFintech = fint_name;
        Fintech getName = fintechRepository.findByFintNameOptional(nameFintech).orElse(null);
        FintechIdForUserDtoResponse dto = new FintechIdForUserDtoResponse();

        if(getName == null){
            throw new UserNotFoundException("Tidak Terdapat Nama Fintech: " + fint_name);
        }else{
            Fintech fintech = getName;
            dto.setFint_entityid(fintech.getFint_entityid());
        }

        return dto;
    }


    @Override
    public List<FintechDtoResponse> getAll() {
        List<Fintech> listFintech = fintechRepository.findAll();
        List<FintechDtoResponse> listDto = new ArrayList<>();
        if(listFintech.isEmpty()){
            throw new EntityNotFoundException("Data Fintech Masih Kosong");
        }else{
            for (Fintech fint : listFintech) {
                FintechDtoResponse fintechDto = FintechMapper.convertEntityToDto(fint);
                listDto.add(fintechDto);
            }
        }
      
        return listDto;
    }

    @Override
    public FintechDtoResponse getById(Long id) {
        Fintech idFintech = fintechRepository.findById(id).orElseThrow(() 
        -> new EntityNotFoundException(" Tidak terdapat id : " + id));
        return FintechMapper.convertEntityToDto(idFintech);
    }

    @Override
    public FintechDtoResponse save(FintechDtoResponse fintechDto) {

        return null;
    }
}
