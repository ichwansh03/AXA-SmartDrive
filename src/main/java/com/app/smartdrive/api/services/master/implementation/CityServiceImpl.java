package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.services.master.CityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository repository;

    @Override
    public Cities getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("City ID : " + aLong + " Not Found"));
    }

    @Override
    public List<Cities> getAll() {
        return repository.findAll();
    }

    @Override
    public Cities save(Cities entity) {
        return repository.save(entity);
    }
}
