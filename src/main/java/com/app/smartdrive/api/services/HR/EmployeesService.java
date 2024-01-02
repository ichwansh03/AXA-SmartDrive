package com.app.smartdrive.api.services.HR;

import org.springframework.data.domain.Page;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.entities.hr.Employees;
import org.springframework.data.domain.Pageable;

public interface EmployeesService {
  Page<EmployeesResponseDto> searchEmployees(String value, int page, int size) ;
  EmployeesResponseDto createEmployee(EmployeesRequestDto employeesDto);
  EmployeesResponseDto updateEmployee(Long employeeId, EmployeesRequestDto employeesDto);
  void deleteEmployeesById(Long empEntitiyid) ;
  Employees getById(Long id);
  Page<EmployeesResponseDto> getAll(Pageable pageable);
}
