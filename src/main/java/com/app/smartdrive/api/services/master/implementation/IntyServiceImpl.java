package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.IntyReq;
import com.app.smartdrive.api.dto.master.response.IntyRes;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.IntyRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("intyServiceImpl")
public class IntyServiceImpl implements MasterService<IntyRes, IntyReq, String> {
    private final IntyRepository repository;

    @Override
    public IntyRes getById(String s) {
        return TransactionMapper.mapEntityToDto(repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Insurance Type ID : " + s + " Not Found")), IntyRes.class);
    }

    @Override
    public List<IntyRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), IntyRes.class);
    }

    @Override
    public IntyRes update(String s, IntyReq intyReq) {
        InsuranceType insuranceType = repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Insurance Type ID : " + s + " Not Found"));
        insuranceType = repository.save(TransactionMapper.mapDtoToEntity(intyReq, insuranceType));
        return TransactionMapper.mapEntityToDto(insuranceType, IntyRes.class);
    }

    @Override
    @Transactional
    public IntyRes save(IntyReq entity) {
        InsuranceType insuranceType = repository.save(TransactionMapper.mapDtoToEntity(entity, new InsuranceType()));
        return TransactionMapper.mapEntityToDto(insuranceType, IntyRes.class);
    }
}
