package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.Category;
import com.app.smartdrive.api.repositories.master.CateRepository;
import com.app.smartdrive.api.services.master.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CateRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category saveOne() {
        return null;
    }
}
