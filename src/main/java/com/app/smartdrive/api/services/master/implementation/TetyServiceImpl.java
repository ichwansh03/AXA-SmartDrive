package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.TetyReq;
import com.app.smartdrive.api.dto.master.response.TetyRes;
import com.app.smartdrive.api.entities.master.TemplateType;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TetyRepository;
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
@Qualifier("tetyServiceImpl")
public class TetyServiceImpl implements MasterService<TetyRes, TetyReq, Long> {
    private final TetyRepository repository;

    @Override
    public TetyRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Template Type : " + aLong + " Not Found")), TetyRes.class);
    }

    @Override
    public Page<TetyRes> getAll(Pageable pageable) {
        Page<TemplateType> templateTypes = repository.findAll(pageable);
        List<TetyRes> tetyRes = templateTypes.getContent().stream().map(
                templateType -> TransactionMapper.mapEntityToDto(templateType, TetyRes.class)
        ).toList();
        return new PageImpl<>(tetyRes, pageable, templateTypes.getTotalElements());
    }

    @Override
    public TetyRes update(Long aLong, TetyReq tetyReq) {
        TemplateType templateType = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Template Type : " + aLong + " Not Found"));
        templateType = repository.save(TransactionMapper.mapDtoToEntity(tetyReq, templateType));
        return TransactionMapper.mapEntityToDto(templateType, TetyRes.class);
    }

    @Override
    @Transactional
    public TetyRes save(TetyReq entity) {
        TemplateType templateType = new TemplateType();
        templateType.setTetyGroup(String.valueOf(entity.getTetyGroup()));
        templateType.setTetyName(String.valueOf(entity.getTetyName()));
        repository.save(templateType);
        return TransactionMapper.mapEntityToDto(templateType, TetyRes.class);
    }
}
