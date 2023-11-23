package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.CarModel;
import com.app.smartdrive.api.repositories.master.CarmRepository;
import com.app.smartdrive.api.services.master.CarmService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarmServiceImpl implements CarmService {
    private final CarmRepository repository;

    @Override
    public CarModel getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Model ID : " + aLong + " Not Found"));
    }

    @Override
    public List<CarModel> getAll() {
        return repository.findAll();
    }

    @Override
    public CarModel save(CarModel entity) {
        return repository.save(entity);
    }
}
