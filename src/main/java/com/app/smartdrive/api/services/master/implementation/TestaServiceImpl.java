package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.TestaReq;
import com.app.smartdrive.api.dto.master.response.TestaRes;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("testaServiceImpl")
public class TestaServiceImpl implements MasterService<TestaRes, TestaReq, Long> {
    private final TestaRepository repository;

    @Override
    public TestaRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Service Task : " + aLong + " Not Found")), TestaRes.class);
    }

    @Override
    public List<TestaRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), TestaRes.class);
    }

    @Override
    public TestaRes update(Long aLong, TestaReq testaReq) {
        TemplateServiceTask templateServiceTask = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Service Task : " + aLong + " Not Found"));
        templateServiceTask = repository.save(TransactionMapper.mapDtoToEntity(testaReq, templateServiceTask));
        return TransactionMapper.mapEntityToDto(templateServiceTask, TestaRes.class);
    }

    @Override
    @Transactional
    public TestaRes save(TestaReq entity) {
        TemplateServiceTask templateInsurancePremi = repository.save(TransactionMapper.mapDtoToEntity(entity, new TemplateServiceTask()));
        return TransactionMapper.mapEntityToDto(templateInsurancePremi, TestaRes.class);
    }
}
