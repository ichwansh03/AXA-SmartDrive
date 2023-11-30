package com.app.smartdrive.api.controllers.HR;



import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.partner.Partner;
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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployeesById (@RequestParam Long emp_entityid) {
            employeesService.deleteById(emp_entityid);
            return new ResponseEntity<>("Employees deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<EmployeesDto> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeesDto updatedEmployeeDto) {
        EmployeesDto updatedEmployee = employeesService.updateEmployee(employeeId, updatedEmployeeDto);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @GetMapping("/search")
    public Page<Employees> searchEmployees(
            @RequestParam(value = "value") String value,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return employeesService.searchEmployees(value, page, size);
    }

    

    
}
