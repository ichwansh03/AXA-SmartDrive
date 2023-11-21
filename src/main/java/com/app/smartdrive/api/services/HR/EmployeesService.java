package com.app.smartdrive.api.services.HR;

import java.time.LocalDateTime;

import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeesService extends BaseService<Employees, Long>  {
 public Employees addEmployee(EmployeesDto employeesDto);
}
