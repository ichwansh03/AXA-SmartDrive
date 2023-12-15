package com.app.smartdrive.api.services.HR;

import java.util.List;
import org.springframework.data.domain.Page;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeesService extends BaseService<Employees, Long>  {
 public List<EmployeesRequestDto> getAllEmployeesDto();
 public Page<Employees> searchEmployees(String value, int page, int size) ;
 public Employees createEmployee(EmployeesRequestDto employeesDto);
 public Employees editEmployee(Long employeeId, EmployeesRequestDto employeesDto);
 public List<EmployeesResponseDto> getAllDto();
 public void deleteEmployeesById(Long empEntitiyid) ;
}
