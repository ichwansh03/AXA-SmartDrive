package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.Provinsi;
import com.smartdrive.masterservice.repositories.ProvRepository;
import com.smartdrive.masterservice.services.ProvService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvServiceImpl implements ProvService {
    private final ProvRepository repository;

    @Override
    public Provinsi getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Provinsi ID : " + aLong + " Not Found"));
    }

    @Override
    public List<Provinsi> getAll() {
        return repository.findAll();
    }

    @Override
    public Provinsi save(Provinsi entity) {
        return repository.save(entity);
    }
}
