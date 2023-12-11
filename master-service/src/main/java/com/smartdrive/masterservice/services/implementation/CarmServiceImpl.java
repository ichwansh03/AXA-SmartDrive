package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.CarModel;
import com.smartdrive.masterservice.repositories.CarmRepository;
import com.smartdrive.masterservice.services.CarmService;
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
