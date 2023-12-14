package com.app.smartdrive.api.services.HR;

import java.util.List;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;

import com.app.smartdrive.api.services.BaseService;

public interface BatchEmployeeSalaryService extends BaseService<BatchEmployeeSalary, Long>{
    public BatchEmployeeSalary createOne(Long id);

    public List<BatchEmployeeSalary> getAllTransNull();
}   
