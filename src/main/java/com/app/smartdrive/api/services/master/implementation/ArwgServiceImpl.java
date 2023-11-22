package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.services.master.ArwgService;
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
        return repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Category ID : " + s + " Not Found !"));
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
