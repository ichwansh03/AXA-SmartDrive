package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.CarBrand;
import com.smartdrive.masterservice.repositories.CabrRepository;
import com.smartdrive.masterservice.services.CarbService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarbServiceImpl implements CarbService {
    private final CabrRepository repository;

    @Override
    public CarBrand getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Car Brand ID : " + aLong + " Not Found"));
    }

    @Override
    public List<CarBrand> getAll() {
        return repository.findAll();
    }

    @Override
    public CarBrand save(CarBrand entity) {
        return repository.save(entity);
    }
}
