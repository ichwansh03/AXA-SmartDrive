package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.TewoReq;
import com.app.smartdrive.api.dto.master.response.TewoRes;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("tewoServiceImpl")
public class TewoServiceImpl implements MasterService<TewoRes, TewoReq, Long> {
    private final TewoRepository repository;

    @Override
    public TewoRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Task Work Order : " + aLong + " Not Found")), TewoRes.class);
    }

    @Override
    public List<TewoRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), TewoRes.class);
    }

    @Override
    @Transactional
    public TewoRes save(TewoReq entity) {
        TemplateTaskWorkOrder templateWorkOrder = repository.save(TransactionMapper.mapDtoToEntity(entity, new TemplateTaskWorkOrder()));
        return TransactionMapper.mapEntityToDto(templateWorkOrder, TewoRes.class);
    }
}
