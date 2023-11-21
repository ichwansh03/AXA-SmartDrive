package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.CarBrandDto;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.repositories.master.CabrRepository;
import com.app.smartdrive.api.services.master.CarbService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarbServiceImpl implements CarbService {
    private final CabrRepository repository;

    @Override
    public List<CarBrandDto> findAllCarBrand() {
        List<CarBrand> carBrands = repository.findAll();
        List<CarBrandDto> carBrandDtos = carBrands.stream().map(car -> {
            return new CarBrandDto(car.getCabrID(), car.getCabrName());
        }).toList();
        return carBrandDtos;
    }

    @Override
    public CarBrandDto findCarBrandById(Long id) {
        CarBrand carBrand = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car Brand ID : " + id + " Not Found !"));
        return new CarBrandDto(
                carBrand.getCabrID(),
                carBrand.getCabrName()
        );
    }

    @Override
    public CarBrand createBrand(CarBrand carBrand) {
        return repository.save(carBrand);
    }
}
