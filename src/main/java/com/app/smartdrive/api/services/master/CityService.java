package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.services.BaseService;

import java.util.List;

public interface CityService extends BaseService<Cities, Long> {
    List<CitiesDto> findAll();
}
