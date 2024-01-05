package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.RegpReq;
import com.app.smartdrive.api.dto.master.response.RegpRes;
import com.app.smartdrive.api.entities.master.RegionPlat;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.RegpRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("regpServiceImpl")
public class RegpServiceImpl implements MasterService<RegpRes, RegpReq, String> {
    private final RegpRepository repository;

    @Override
    public RegpRes getById(String s) {
        return TransactionMapper.mapEntityToDto(repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Region Plat : " + s + " Not Found")), RegpRes.class);
    }

    @Override
    public List<RegpRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), RegpRes.class);
    }

    @Override
    public RegpRes update(String s, RegpReq regpReq) {
        RegionPlat regionPlat = repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Region Plat : " + s + " Not Found"));
        regionPlat = repository.save(TransactionMapper.mapDtoToEntity(regpReq, regionPlat));
        return TransactionMapper.mapEntityToDto(regionPlat, RegpRes.class);
    }

    @Override
    @Transactional
    public RegpRes save(RegpReq entity) {
        RegionPlat regionPlat = repository.save(TransactionMapper.mapDtoToEntity(entity, new RegionPlat()));
        return TransactionMapper.mapEntityToDto(regionPlat, RegpRes.class);
    }
}
