package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.master.InsuranceTypeDto;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.services.BaseService;

import java.util.List;

public interface IntyService extends BaseService<InsuranceType, String> {
    List<InsuranceTypeDto> findAllInsuranceType();
}
