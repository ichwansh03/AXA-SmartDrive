package com.app.smartdrive.api.controllers.HR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/eawo")
public class EmployeeAreaWorkgroupController {
    
    private final EmployeeAreaWorkgroupService employeeAreaWorkgroupService;
    
    @PostMapping("/add")
    public ResponseEntity<EmployeeAreaWorkgroupDto> addEmployeeAreaWorkgroup(@ModelAttribute EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto) {
        
            EmployeeAreaWorkgroupDto resultDto = employeeAreaWorkgroupService.addEmployeeAreaWorkgroup(employeeAreaWorkgroupDto);
            return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
        
    }
}
