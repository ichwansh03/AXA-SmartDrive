package com.app.smartdrive.api.controllers.HR;



import java.net.URISyntaxException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.services.HR.EmployeesService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/hr")
public class EmployeesController {
     private final EmployeesService employeesService;

     

     @PostMapping("/add")
     public ResponseEntity<?> addEmployee(@ModelAttribute EmployeesDto employeesDto) {
            EmployeesDto addedEmployee = employeesService.addEmployee(employeesDto);
            return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
        
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeesDto> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeesDto updatedEmployeeDto) {
        EmployeesDto updatedEmployee = employeesService.updateEmployee(employeeId, updatedEmployeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }
    
}
