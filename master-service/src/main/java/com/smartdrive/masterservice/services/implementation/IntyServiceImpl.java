package com.smartdrive.masterservice.services.implementation;

import com.smartdrive.masterservice.entities.InsuranceType;
import com.smartdrive.masterservice.repositories.IntyRepository;
import com.smartdrive.masterservice.services.IntyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IntyServiceImpl implements IntyService {
    private final IntyRepository repository;

    @Override
    public InsuranceType getById(String s) {
        return repository.findById(s).orElseThrow(() -> new EntityNotFoundException("Insurance Type ID : " + s + " Not Found"));
    }

    @Override
    public List<InsuranceType> getAll() {
        return repository.findAll();
    }

    @Override
    public InsuranceType save(InsuranceType entity) {
        return repository.save(entity);
    }
}
