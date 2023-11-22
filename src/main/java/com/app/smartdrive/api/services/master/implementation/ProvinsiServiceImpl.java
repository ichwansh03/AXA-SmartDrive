package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.ProvinsiDto;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.services.master.ProvinsiService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinsiServiceImpl implements ProvinsiService {
    private final ProvRepository repository;

    @Override
    public Provinsi getById(Long aLong) {
        return repository.findById(aLong).orElseThrow(() -> new EntityNotFoundException("Provinsi ID : " + aLong + " Not Found"));
    }

    @Override
    public List<ProvinsiDto> findAll() {
        List<Provinsi> provinsi = repository.findAll();
        List<ProvinsiDto> provinsiDtos = provinsi.stream().map(provinsi1 -> {
            return new ProvinsiDto(
                    provinsi1.getProvId(),
                    provinsi1.getProvName(),
                    provinsi1.getProv_zones_id()
            );
        }).toList();
        return provinsiDtos;
    }

    @Override
    public List<Provinsi> getAll() {
        return null;
    }

    @Override
    public Provinsi save(Provinsi entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
