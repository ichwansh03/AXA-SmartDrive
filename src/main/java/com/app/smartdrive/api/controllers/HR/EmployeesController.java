package com.app.smartdrive.api.controllers.HR;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.HR.EmployeesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeesController {
     private final EmployeesService employeesService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> deleteEmployeesById (@PathVariable("id") Long id) {
        employeesService.deleteEmployeesById(id);
        return new ResponseEntity<>("Employees deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeesResponseDto> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody EmployeesRequestDto updatedEmployeeDto) {
        Employees employees = employeesService.editEmployee(id, updatedEmployeeDto);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(employees, EmployeesResponseDto.class));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeesResponseDto> createEmployee(@Valid @RequestBody EmployeesRequestDto employeesRequestDto) {
        Employees employees = employeesService.createEmployee(employeesRequestDto);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(employees, EmployeesResponseDto.class));
    }


    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin')")
    public Page<Employees> searchEmployees(
            @RequestParam(value = "value") String value,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return employeesService.searchEmployees(value, page, size);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeesResponseDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employees> employeesPage = employeesService.getAll(pageable);

        List<EmployeesResponseDto> employeesResponseList = employeesPage.getContent().stream()
                .map(emp -> TransactionMapper.mapEntityToDto(emp, EmployeesResponseDto.class))
                .toList();

        return ResponseEntity.ok(new PageImpl<>(employeesResponseList, pageable, employeesPage.getTotalElements()));
    }


}
