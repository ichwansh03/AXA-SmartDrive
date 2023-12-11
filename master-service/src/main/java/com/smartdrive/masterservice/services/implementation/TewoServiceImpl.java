package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.TemplateTaskWorkOrder;
import com.smartdrive.masterservice.repositories.TewoRepository;
import com.smartdrive.masterservice.services.TewoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TewoServiceImpl implements TewoService {
    private final TewoRepository repository;

    @Override
    public TemplateTaskWorkOrder getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Task Work Order : " + aLong + " Not Found"));
    }

    @Override
    public List<TemplateTaskWorkOrder> getAll() {
        return repository.findAll();
    }

    @Override
    public TemplateTaskWorkOrder save(TemplateTaskWorkOrder entity) {
        return repository.save(entity);
    }
}
