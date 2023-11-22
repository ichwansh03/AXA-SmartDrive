package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.RegionPlat;
import com.app.smartdrive.api.repositories.master.RegpRepository;
import com.app.smartdrive.api.services.master.RegpService;
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

    @Override
    public void deleteById(String s) {

    }
}
