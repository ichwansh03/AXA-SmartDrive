package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.repositories.master.CateRepository;
import com.app.smartdrive.api.services.master.CateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CateService {
    private final CateRepository repository;

    @Override
    public Category getById(Long aLong) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
