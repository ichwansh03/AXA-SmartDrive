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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<ProvRes> getAll(Pageable pageable) {
        Page<Provinsi> provinsi = repository.findAll(pageable);
        List<ProvRes> provRes = provinsi.getContent().stream().map(
                prov -> TransactionMapper.mapEntityToDto(prov, ProvRes.class)
        ).toList();
        return new PageImpl<>(provRes, pageable, provinsi.getTotalElements());
    }

    @Override
    public ProvRes update(Long aLong, ProvReq provReq) {
        Provinsi provinsi = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Provinsi ID : " + aLong + " Not Found"));
        provinsi = repository.save(TransactionMapper.mapDtoToEntity(provReq, provinsi));
        return TransactionMapper.mapEntityToDto(provinsi, ProvRes.class);
    }

    @Override
    @Transactional
    public ProvRes save(ProvReq entity) {
        Provinsi provinsi = repository.save(TransactionMapper.mapDtoToEntity(entity, new Provinsi()));
        return TransactionMapper.mapEntityToDto(provinsi, ProvRes.class);
    }
}
