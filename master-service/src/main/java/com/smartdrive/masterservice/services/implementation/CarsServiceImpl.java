package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.CarSeries;
import com.smartdrive.masterservice.repositories.CarsRepository;
import com.smartdrive.masterservice.services.CarsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public CarSeries save(CarSeries entity) {
        return repository.save(entity);
    }
}
