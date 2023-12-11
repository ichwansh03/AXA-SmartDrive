package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.TemplateType;
import com.smartdrive.masterservice.repositories.TetyRepository;
import com.smartdrive.masterservice.services.TetyService;
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
