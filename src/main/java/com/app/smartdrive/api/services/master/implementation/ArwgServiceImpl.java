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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<ArwgRes> getAll(Pageable pageable) {
        Page<AreaWorkGroup> areaWorkGroups = repository.findAll(pageable);
        List<ArwgRes> arwgRes = areaWorkGroups.getContent().stream().map(
                arwg -> TransactionMapper.mapEntityToDto(arwg, ArwgRes.class)
        ).toList();
        return new PageImpl<>(arwgRes, pageable, areaWorkGroups.getTotalElements());
    }


    @Override
    public ArwgRes save(ArwgReq request) {
        AreaWorkGroup areaWorkGroup = repository.save(TransactionMapper.mapDtoToEntity(request, new AreaWorkGroup()));
        return TransactionMapper.mapEntityToDto(areaWorkGroup, ArwgRes.class);
    }

    @Override
    public ArwgRes update(String s, ArwgReq arwgReq) {
        AreaWorkGroup arwg = repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Area Workgroup ID : " + s + " Not Found !"));
        arwg = repository.save(TransactionMapper.mapDtoToEntity(arwgReq, arwg));
        return TransactionMapper.mapEntityToDto(arwg, ArwgRes.class);
    }

    @Override
    @Transactional
    public void deleteById(String s) {
        repository.deleteById(s);
    }
}
