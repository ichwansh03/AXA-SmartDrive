package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.CarsReq;
import com.app.smartdrive.api.dto.master.response.CarsRes;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CarsRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("carsServiceImpl")
public class CarsServiceImpl implements MasterService<CarsRes, CarsReq, Long> {
    private final CarsRepository repository;

    @Override
    public CarsRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Series ID : " + aLong + " Not Found")), CarsRes.class);
    }

    @Override
    public List<CarsRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), CarsRes.class);
    }

    @Override
    public CarsRes update(Long aLong, CarsReq carsReq) {
        CarSeries carSeries = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Series ID : " + aLong + " Not Found"));
        repository.save(TransactionMapper.mapDtoToEntity(carsReq,carSeries));
        return TransactionMapper.mapEntityToDto(carSeries, CarsRes.class);
    }

    @Override
    @Transactional
    public CarsRes save(CarsReq entity) {
        CarSeries carSeries = repository.save(TransactionMapper.mapDtoToEntity(entity, new CarSeries()));
        return TransactionMapper.mapEntityToDto(carSeries, CarsRes.class);
    }
}
