package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.master.request.ArwgReq;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.services.master.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("arwgServiceImpl")
public class ArwgServiceImpl implements MasterService<ArwgRes, ArwgReq, String> {
    private final ArwgRepository repository;

    @Override
    public ArwgRes getById(String s) {
        AreaWorkGroup arwg = repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Area Workgroup ID : " + s + " Not Found !"));
        return TransactionMapper.mapEntityToDto(arwg, ArwgRes.class);
    }

    @Override
    public List<ArwgRes> getAll() {
        return TransactionMapper.mapEntityListToDtoList(repository.findAll(), ArwgRes.class);
    }


    @Override
    public ArwgRes save(ArwgReq request) {
        AreaWorkGroup areaWorkGroup = repository.save(TransactionMapper.mapDtoToEntity(request, new AreaWorkGroup()));
        return TransactionMapper.mapEntityToDto(areaWorkGroup, ArwgRes.class);
    }

    @Override
    public ArwgRes update(String s, ArwgReq arwgReq) {
        AreaWorkGroup areaWorkGroup = repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Area Workgroup ID : " + s + " Not Found !"));
        repository.save(TransactionMapper.mapDtoToEntity(arwgReq, areaWorkGroup));
        return TransactionMapper.mapEntityToDto(areaWorkGroup, ArwgRes.class);
    }

    @Override
    @Transactional
    public void deleteById(String s) {
        repository.deleteById(s);
    }
}
