package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.services.master.TestaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestaServiceImpl implements TestaService {
    private final TestaRepository repository;

    @Override
    public TemplateServiceTask getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Service Task : " + aLong + " Not Found"));
    }

    @Override
    public List<TemplateServiceTask> getAll() {
        return repository.findAll();
    }

    @Override
    public TemplateServiceTask save(TemplateServiceTask entity) {
        return repository.save(entity);
    }
}
