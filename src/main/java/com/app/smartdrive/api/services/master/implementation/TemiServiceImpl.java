package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.TemiReq;
import com.app.smartdrive.api.dto.master.response.TemiRes;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TemiRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("temiServiceImpl")
public class TemiServiceImpl implements MasterService<TemiRes, TemiReq, Long> {
    private final TemiRepository repository;

    @Override
    public TemiRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Template Insurance Premi ID : " + aLong + " Not Found")), TemiRes.class);
    }

    @Override
    public List<TemiRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), TemiRes.class);
    }

    @Override
    public TemiRes update(Long aLong, TemiReq temiReq) {
        TemplateInsurancePremi templateInsurancePremi = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Template Insurance Premi ID : " + aLong + " Not Found"));
        repository.save(TransactionMapper.mapDtoToEntity(temiReq,templateInsurancePremi));
        return TransactionMapper.mapEntityToDto(templateInsurancePremi, TemiRes.class);
    }

    @Override
    @Transactional
    public TemiRes save(TemiReq entity) {
        TemplateInsurancePremi templateInsurancePremi = repository.save(TransactionMapper.mapDtoToEntity(entity, new TemplateInsurancePremi()));
        return TransactionMapper.mapEntityToDto(templateInsurancePremi, TemiRes.class);
    }
}
