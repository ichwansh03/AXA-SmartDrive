package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.AreaWorkGroup;
import com.smartdrive.masterservice.repositories.ArwgRepository;
import com.smartdrive.masterservice.services.ArwgService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArwgServiceImpl implements ArwgService {
    private final ArwgRepository repository;

    @Override
    public AreaWorkGroup getById(String s) {
        return repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Area Workgroup ID : " + s + " Not Found !"));
    }

    @Override
    public List<AreaWorkGroup> getAll() {
        return repository.findAll();
    }

    @Override
    public AreaWorkGroup save(AreaWorkGroup entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(String s) {
        repository.deleteById(s);
    }
}
