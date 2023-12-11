package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.Category;
import com.smartdrive.masterservice.repositories.CateRepository;
import com.smartdrive.masterservice.services.CateService;
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
