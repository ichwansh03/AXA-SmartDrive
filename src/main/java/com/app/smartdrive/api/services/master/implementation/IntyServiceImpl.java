package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.InsuranceTypeDto;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.repositories.master.IntyRepository;
import com.app.smartdrive.api.services.master.IntyService;
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
        return null;
    }

    @Override
    public InsuranceType save(InsuranceType entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(String s) {}

    @Override
    public List<InsuranceTypeDto> findAllInsuranceType() {
        List<InsuranceType> insuranceType = repository.findAll();
        List<InsuranceTypeDto> insuranceTypeDtos = insuranceType.stream().map(inty -> {
            return new InsuranceTypeDto(
                    inty.getIntyName(),
                    inty.getIntyName()
            );
        }).toList();
        return insuranceTypeDtos;
    }
}
