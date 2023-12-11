package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.Zones;
import com.smartdrive.masterservice.repositories.ZonesRepository;
import com.smartdrive.masterservice.services.ZoneService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {
    private final ZonesRepository repository;

    @Override
    public Zones getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Zone ID : " + aLong + " Not Found !"));
    }

    @Override
    public List<Zones> getAll() {
        return repository.findAll();
    }

    @Override
    public Zones save(Zones entity) {
        return repository.save(entity);
    }
}
