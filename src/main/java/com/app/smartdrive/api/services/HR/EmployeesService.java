package com.app.smartdrive.api.services.HR;

import java.time.LocalDateTime;
import java.util.List;


import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeesService extends BaseService<Employees, Long>  {
 public EmployeesDto addEmployee(EmployeesDto employeesDto);
 public EmployeesDto updateEmployee(Long employeeId, EmployeesDto updatedEmployeeDto);
 public List<EmployeesDto> getAllEmployeesDto();
 public List<Employees> getAllByEmployeeName(String employeeName);
}
