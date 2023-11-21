package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.master.CarBrandDto;
import com.app.smartdrive.api.entities.master.CarBrand;

import java.util.List;

public interface CarbService {
    List<CarBrandDto> findAllCarBrand();

    CarBrand createBrand(CarBrand carBrand);

    CarBrandDto findCarBrandById(Long id);
}
