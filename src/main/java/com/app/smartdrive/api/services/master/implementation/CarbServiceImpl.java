package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.CarbReq;
import com.app.smartdrive.api.dto.master.response.CarbRes;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CabrRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("carbServiceImpl")
public class CarbServiceImpl implements MasterService<CarbRes, CarbReq, Long> {
    private final CabrRepository repository;

    @Override
    public CarbRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Brand ID : " + aLong + " Not Found")), CarbRes.class);
    }

    @Override
    public List<CarbRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), CarbRes.class);
    }

    @Override
    public CarbRes update(Long aLong, CarbReq carbReq) {
        CarBrand carBrand = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Brand ID : " + aLong + " Not Found"));
        repository.save(TransactionMapper.mapDtoToEntity(carbReq, carBrand));
        return TransactionMapper.mapEntityToDto(carBrand, CarbRes.class);
    }

    @Override
    @Transactional
    public CarbRes save(CarbReq entity) {
        CarBrand carBrand = repository.save(TransactionMapper.mapDtoToEntity(entity, new CarBrand()));
        return TransactionMapper.mapEntityToDto(carBrand, CarbRes.class);
    }
}
