package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.request.CarmReq;
import com.app.smartdrive.api.dto.master.response.CarmRes;
import com.app.smartdrive.api.entities.master.CarModel;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.CarmRepository;
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
@Qualifier("carmServiceImpl")
public class CarmServiceImpl implements MasterService<CarmRes, CarmReq, Long> {
    private final CarmRepository repository;

    @Override
    public CarmRes getById(Long aLong) {
        return TransactionMapper.mapEntityToDto(repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Model ID : " + aLong + " Not Found")), CarmRes.class);
    }

    @Override
    public Page<CarmRes> getAll(Pageable pageable) {
        Page<CarModel> carModels = repository.findAll(pageable);
        List<CarmRes> carmRes = carModels.getContent().stream().map(
                model -> TransactionMapper.mapEntityToDto(model, CarmRes.class)
        ).toList();
        return new PageImpl<>(carmRes, pageable, carModels.getTotalElements());
    }

    @Override
    public CarmRes update(Long aLong, CarmReq carmReq) {
        CarModel carModel = repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Model ID : " + aLong + " Not Found"));
        carModel = repository.save(TransactionMapper.mapDtoToEntity(carmReq, carModel));
        return TransactionMapper.mapEntityToDto(carModel, CarmRes.class);
    }

    @Override
    @Transactional
    public CarmRes save(CarmReq entity) {
        CarModel carModel = repository.save(TransactionMapper.mapDtoToEntity(entity, new CarModel()));
        return TransactionMapper.mapEntityToDto(carModel, CarmRes.class);
    }
}
