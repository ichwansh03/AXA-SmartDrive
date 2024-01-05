package com.app.smartdrive.api.controllers.HR;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.services.HR.EmployeesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/employees")
public class EmployeesController {
     private final EmployeesService employeesService;

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeesRequestDto employeesRequestDto) {
        return ResponseEntity.status(201).body(employeesService.createEmployee(employeesRequestDto));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody EmployeesRequestDto updatedEmployeeDto) {
        return ResponseEntity.status(200).body(employeesService.updateEmployee(id, updatedEmployeeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeesById (@PathVariable Long id) {
        employeesService.deleteEmployeesById(id);
        return new ResponseEntity<>("Employees deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/search")
        public ResponseEntity<?> searchEmployees(
            @RequestParam(value = "value") String value,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(employeesService.searchEmployees(value,page,size));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(employeesService.getAll(pageable));
    }


}
