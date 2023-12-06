package com.app.smartdrive.api.services.HR;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeesService extends BaseService<Employees, Long>  {
//  public EmployeesDto addEmployee(EmployeesDto employeesDto);
//  public CreateEmployeesDto updateEmployee(Long employeeId, CreateEmployeesDto updatedEmployeeDto);
 public List<EmployeesRequestDto> getAllEmployeesDto();
 public List<Employees> getAllByEmployeeName(String employeeName);


 
 public Page<Employees> searchEmployees(String value, int page, int size) ;
 public EmployeesRequestDto createEmployee(EmployeesRequestDto employeesDto);
 public EmployeesRequestDto editEmployee(Long employeeId, EmployeesRequestDto employeesDto); 

}
