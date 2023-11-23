package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.services.master.ProvService;
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
