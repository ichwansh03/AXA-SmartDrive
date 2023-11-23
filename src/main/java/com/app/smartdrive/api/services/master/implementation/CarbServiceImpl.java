package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.repositories.master.CabrRepository;
import com.app.smartdrive.api.services.master.CarbService;
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
