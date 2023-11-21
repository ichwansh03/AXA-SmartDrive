package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.CarModelDto;
import com.app.smartdrive.api.entities.master.CarBrand;
import com.app.smartdrive.api.entities.master.CarModel;
import com.app.smartdrive.api.repositories.master.CarmRepository;
import com.app.smartdrive.api.services.master.CarmService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarmServiceImpl implements CarmService {
    private final CarmRepository repository;

    @Override
    public List<CarModelDto> findAllCarModel() {
        List<CarModel> carModels = repository.findAll();
        List<CarModelDto> carModelDtos = carModels.stream().map(car -> {
            return new CarModelDto(car.getCarmId(), car.getCarmName(), car.getCarmCarbId());
        }).toList();
        return carModelDtos;
    }

    @Override
    public CarModelDto findCarModelById(Long id) {
        CarModel carModel = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car Model ID : " + id + " Not Found !"));
        return new CarModelDto(
                carModel.getCarmId(),
                carModel.getCarmName(),
                carModel.getCarmCarbId()
        );
    }

    @Override
    public CarModel createModel(CarModel carModel) {
        return repository.save(carModel);
    }
}
