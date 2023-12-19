package com.app.smartdrive.api.services.HR;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeeSalaryDetailService extends BaseService<EmployeeSalaryDetail, Long>{
    public List<EmployeeSalaryDetail> generateSalaryDetails(Long entityId);

}