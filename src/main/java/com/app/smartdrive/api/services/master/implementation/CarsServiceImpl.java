package com.app.smartdrive.api.services.master.implementation;

import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.repositories.master.CarsRepository;
import com.app.smartdrive.api.services.master.CarsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarsServiceImpl implements CarsService {
    private final CarsRepository repository;
    @Override
    public List<CarSeriesDto> findAllCarSeries() {
        List<CarSeries> carSeries = repository.findAll();
        List<CarSeriesDto> carSeriesDtos =  carSeries.stream().map(car -> {
            return new CarSeriesDto(car.getCarsId(), car.getCarsName(), car.getCarsPassenger(), car.getCarsCarmId());
        }).toList();
        return carSeriesDtos;
    }
    @Override
    public CarSeries findCarSeriesById(Long id) {
        CarSeries carSeries = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Car Series ID : " + id + " Not Found !"));
        return carSeries;
    }

    @Override
    public CarSeries createSeries(CarSeries carSeries) {
        return repository.save(carSeries);
    }
}
