package com.app.smartdrive.api.services.HR;

import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.entities.hr.JobType;

import java.util.List;

public interface JobTypeService {
    EmployeesJobTypeResponseDto getById(String id);

    List<EmployeesJobTypeResponseDto> getAll();
}
