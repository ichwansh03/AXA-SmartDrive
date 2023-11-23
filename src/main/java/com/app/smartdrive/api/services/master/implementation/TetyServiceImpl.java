package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.TemplateType;
import com.app.smartdrive.api.repositories.master.TetyRepository;
import com.app.smartdrive.api.services.master.TetyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TetyServiceImpl implements TetyService {
    private final TetyRepository repository;

    @Override
    public TemplateType getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Template Type : " + aLong + " Not Found"));
    }

    @Override
    public List<TemplateType> getAll() {
        return repository.findAll();
    }

    @Override
    public TemplateType save(TemplateType entity) {
        return repository.save(entity);
    }
}
