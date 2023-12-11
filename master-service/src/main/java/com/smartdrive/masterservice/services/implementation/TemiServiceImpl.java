package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.TemplateInsurancePremi;
import com.smartdrive.masterservice.repositories.TemiRepository;
import com.smartdrive.masterservice.services.TemiService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemiServiceImpl implements TemiService {
    private final TemiRepository repository;

    @Override
    public TemplateInsurancePremi getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Template Insurance Premi ID : " + aLong + " Not Found"));
    }

    @Override
    public List<TemplateInsurancePremi> getAll() {
        return repository.findAll();
    }

    @Override
    public TemplateInsurancePremi save(TemplateInsurancePremi entity) {
        return repository.save(entity);
    }
}
