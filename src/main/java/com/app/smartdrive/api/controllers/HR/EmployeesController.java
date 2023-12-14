package com.app.smartdrive.api.controllers.HR;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.services.HR.EmployeesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/hr")
public class EmployeesController {
     private final EmployeesService employeesService;


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployeesById (@RequestParam Long id) {
            employeesService.deleteEmployeesById(id);
            return new ResponseEntity<>("Employees deleted successfully", HttpStatus.OK);
    } 

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeesRequestDto> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeesRequestDto updatedEmployeeDto) {
        EmployeesRequestDto updatedEmployee = employeesService.editEmployee(employeeId, updatedEmployeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeesRequestDto employeesDto) {
    EmployeesRequestDto createdEmployee = employeesService.createEmployee(employeesDto);
    return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @GetMapping("/search")
    public Page<Employees> searchEmployees(
            @RequestParam(value = "value") String value,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return employeesService.searchEmployees(value, page, size);
    }

    @GetMapping("/all")
    public List<Employees> getAllEmployees() {
      return employeesService.getAll();
    }
    
}
