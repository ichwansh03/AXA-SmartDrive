package com.app.smartdrive.api.services.HR;

import java.time.LocalDate;
import java.util.List;

import com.app.smartdrive.api.dto.HR.response.EmployeeSalaryDetailResponseDto;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;

import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;

public interface BatchEmployeeSalaryService{
     BatchEmployeeSalary createOne(Long id);
     List<EmployeeSalaryDetailResponseDto> getAllCommission(Long besaEmpEntityId, LocalDate besaCreateDate);
     List<BatchEmployeeSalary> getAllTransNull();
}   
