package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.master.CarModelDto;
import com.app.smartdrive.api.entities.master.CarModel;

import java.util.List;

public interface CarmService {
    List<CarModelDto> findAllCarModel();
    CarModel findCarModelById(Long id);

    CarModel createModel(CarModel carModel);
}
