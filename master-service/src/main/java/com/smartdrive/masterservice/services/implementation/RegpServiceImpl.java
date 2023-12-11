package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.RegionPlat;
import com.smartdrive.masterservice.repositories.RegpRepository;
import com.smartdrive.masterservice.services.RegpService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegpServiceImpl implements RegpService {
    private final RegpRepository repository;

    @Override
    public RegionPlat getById(String s) {
        return repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Region Plat : " + s + " Not Found"));
    }

    @Override
    public List<RegionPlat> getAll() {
        return repository.findAll();
    }

    @Override
    public RegionPlat save(RegionPlat entity) {
        return repository.save(entity);
    }
}
