package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.ProvReq;
import com.app.smartdrive.api.dto.master.response.ProvRes;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("provServiceImpl")
public class ProvServiceImpl implements MasterService<ProvRes, ProvReq, Long> {
    private final ProvRepository repository;

    @Override
    public ProvRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Provinsi ID : " + aLong + " Not Found")), ProvRes.class);
    }

    @Override
    public List<ProvRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), ProvRes.class);
    }

    @Override
    @Transactional
    public ProvRes save(ProvReq entity) {
        Provinsi provinsi = repository.save(TransactionMapper.mapDtoToEntity(entity, new Provinsi()));
        return TransactionMapper.mapEntityToDto(provinsi, ProvRes.class);
    }
}
