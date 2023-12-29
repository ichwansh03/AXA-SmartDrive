package com.app.smartdrive.api.services.HR;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;

import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.services.BaseService;

public interface BatchEmployeeSalaryService{
    public BatchEmployeeSalary createOne(Long id);
    public List<EmployeeSalaryDetail> getAllCommission(Long besaEmpEntityId, LocalDate besaCreateDate);
    public List<BatchEmployeeSalary> getAllTransNull();
}   
