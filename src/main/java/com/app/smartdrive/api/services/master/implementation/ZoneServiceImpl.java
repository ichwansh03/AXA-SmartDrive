package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.ZoneReq;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.master.response.ZonesRes;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.ZonesRepository;
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
@Qualifier("zoneServiceImpl")
public class ZoneServiceImpl implements MasterService<ZonesRes, ZoneReq, Long> {
    private final ZonesRepository repository;

    @Override
    public ZonesRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Zone ID : " + aLong + " Not Found !")), ZonesRes.class);
    }

    @Override
    public Page<ZonesRes> getAll(Pageable pageable) {
        Page<Zones> zones = repository.findAll(pageable);
        List<ZonesRes> zonesRes = zones.getContent().stream().map(
                zone -> TransactionMapper.mapEntityToDto(zone, ZonesRes.class)
        ).toList();
        return new PageImpl<>(zonesRes, pageable, zones.getTotalElements());
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
