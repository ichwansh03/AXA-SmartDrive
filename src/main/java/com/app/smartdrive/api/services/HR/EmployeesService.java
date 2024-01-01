package com.app.smartdrive.api.services.HR;

import org.springframework.data.domain.Page;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.entities.hr.Employees;
import org.springframework.data.domain.Pageable;

public interface EmployeesService {
  Page<Employees> searchEmployees(String value, int page, int size) ;
  Employees createEmployee(EmployeesRequestDto employeesDto);
  Employees editEmployee(Long employeeId, EmployeesRequestDto employeesDto);
  void deleteEmployeesById(Long empEntitiyid) ;
  Employees getById(Long id);
  Page<Employees> getAll(Pageable pageable);
}
