package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.repositories.master.CateRepository;
import com.app.smartdrive.api.services.master.CateService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CateServiceImpl implements CateService {
    private final CateRepository repository;

    @Override
    public Category getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Category ID : " + aLong + " Not Found !"));
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Category save(Category entity) {
        return repository.save(entity);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

}
