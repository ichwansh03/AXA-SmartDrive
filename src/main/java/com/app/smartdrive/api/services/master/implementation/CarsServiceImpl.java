package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.repositories.master.CarsRepository;
import com.app.smartdrive.api.services.master.CarsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarsServiceImpl implements CarsService {
    private final CarsRepository repository;

    @Override
    public CarSeries getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Series ID : " + aLong + " Not Found"));
    }

    @Override
    public List<CarSeries> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public CarSeries save(CarSeries entity) {
        return repository.save(entity);
    }
}
