package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.mapper.TransactionMapper;
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


//        List<TemplateInsurancePremiDto> template = repository.findAll().
//        return repository.findAll().stream().map(repo -> {
//            return TransactionMapper.mapEntityListToDtoList(repo.getTemplateInsurancePremis(), TemplateInsurancePremiDto.class);
//        });
//        return repository.findAll().stream().map(repo -> {
//            repo.setTemplateInsurancePremis(repo.getTemplateInsurancePremis());
//        }).toList();
        return repository.findAll();
    }

    @Override
    public InsuranceType save(InsuranceType entity) {
        return repository.save(entity);
    }
}
