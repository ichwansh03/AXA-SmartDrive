package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.Cities;
import com.smartdrive.masterservice.repositories.CityRepository;
import com.smartdrive.masterservice.services.CityService;
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
