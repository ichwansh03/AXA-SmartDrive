package com.app.smartdrive.api.services.HR;

import java.util.List;

import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;

public interface EmployeeSalaryDetailService{
     List<EmployeeSalaryDetail> generateSalaryDetails(Long entityId);
}