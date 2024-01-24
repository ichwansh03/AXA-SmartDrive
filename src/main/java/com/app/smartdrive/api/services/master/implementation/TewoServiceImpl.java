package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.TewoReq;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.master.response.TewoRes;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<TewoRes> getAll(Pageable pageable) {
        Page<TemplateTaskWorkOrder> templateTaskWorkOrders = repository.findAll(pageable);
        List<TewoRes> tewoRes = templateTaskWorkOrders.getContent().stream().map(
                templateTaskWorkOrder -> TransactionMapper.mapEntityToDto(templateTaskWorkOrder, TewoRes.class)
        ).toList();
        return new PageImpl<>(tewoRes, pageable, templateTaskWorkOrders.getTotalElements());
    }

    @Override
    public TewoRes update(Long aLong, TewoReq tewoReq) {
        TemplateTaskWorkOrder templateTaskWorkOrder = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Task Work Order : " + aLong + " Not Found"));
        templateTaskWorkOrder = repository.save(TransactionMapper.mapDtoToEntity(tewoReq, templateTaskWorkOrder));
        return TransactionMapper.mapEntityToDto(templateTaskWorkOrder, TewoRes.class);
    }

    @Override
    @Transactional
    public TewoRes save(TewoReq entity) {
        TemplateTaskWorkOrder templateWorkOrder = repository.save(TransactionMapper.mapDtoToEntity(entity, new TemplateTaskWorkOrder()));
        return TransactionMapper.mapEntityToDto(templateWorkOrder, TewoRes.class);
    }
}
