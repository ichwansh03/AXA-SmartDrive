package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.ZoneReq;
import com.app.smartdrive.api.dto.master.response.ZonesRes;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.ZonesRepository;
import com.app.smartdrive.api.services.master.MasterService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("zoneServiceImpl")
public class ZoneServiceImpl implements MasterService<ZonesRes, ZoneReq, Long> {
    private final ZonesRepository repository;

    @Override
    public ZonesRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Zone ID : " + aLong + " Not Found !")), ZonesRes.class);
    }

    @Override
    public List<ZonesRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), ZonesRes.class);
    }

    @Override
    public ZonesRes update(Long aLong, ZoneReq zoneReq) {
        Zones zones = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Zone ID : " + aLong + " Not Found !"));
        zones = repository.save(TransactionMapper.mapDtoToEntity(zoneReq, zones));
        return TransactionMapper.mapEntityToDto(zones, ZonesRes.class);
    }

    @Override
    @Transactional
    public ZonesRes save(ZoneReq entity) {
        Zones zones = repository.save(TransactionMapper.mapDtoToEntity(entity, new Zones()));
        return TransactionMapper.mapEntityToDto(zones, ZonesRes.class);
    }
}
