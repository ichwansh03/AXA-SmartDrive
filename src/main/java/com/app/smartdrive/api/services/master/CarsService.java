package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.entities.master.CarSeries;

import java.util.List;

public interface CarsService {
    List<CarSeriesDto> findAllCarSeries();
    CarSeries findCarSeriesById(Long id);

    CarSeries createSeries(CarSeries carSeries);
}