package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.services.master.TewoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public TemplateTaskWorkOrder save(TemplateTaskWorkOrder entity) {
        return repository.save(entity);
    }
}
