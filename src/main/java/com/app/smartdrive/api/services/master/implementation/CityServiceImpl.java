package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.master.request.CitiesReq;
import com.app.smartdrive.api.dto.master.response.CitiesRes;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.services.master.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("cityServiceImpl")
public class CityServiceImpl implements MasterService<CitiesRes, CitiesReq, Long> {
    private final CityRepository repository;

    @Override
    public CitiesRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("City ID : " + aLong + " Not Found")), CitiesRes.class);
    }

    @Override
    public List<CitiesRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), CitiesRes.class);
    }

    @Override
    @Transactional
    public CitiesRes save(CitiesReq entity) {
        Cities cities = repository.save(TransactionMapper.mapDtoToEntity(entity, new Cities()));
        return TransactionMapper.mapEntityToDto(cities, CitiesRes.class);
    }
}
