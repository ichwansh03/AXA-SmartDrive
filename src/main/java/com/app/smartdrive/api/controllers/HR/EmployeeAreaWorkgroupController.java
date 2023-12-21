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

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/eawg")
public class EmployeeAreaWorkgroupController {
        private final EmployeeAreaWorkgroupService employeeAreaWorkgroupService;
    

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> deleteEmployeesById (@PathVariable("id") Long eawg_id) {
            employeeAreaWorkgroupService.deleteById(eawg_id);
            return new ResponseEntity<>("EAWG deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Page<EmployeesAreaWorkgroupResponseDto>> searchEawg(
            @RequestParam(name = "value") String value,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeAreaWorkgroup> eawgPage = employeeAreaWorkgroupService.searchEawg(value, page, size);
        List<EmployeesAreaWorkgroupResponseDto> listResponse = eawgPage.getContent().stream()
                .map(eawg -> TransactionMapper.mapEntityToDto(eawg, EmployeesAreaWorkgroupResponseDto.class))
                .toList();
        return ResponseEntity.ok(new PageImpl<>(listResponse, pageable, eawgPage.getTotalElements()));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeesAreaWorkgroupResponseDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeAreaWorkgroup> eawgPage = employeeAreaWorkgroupService.getAll(pageable);

        List<EmployeesAreaWorkgroupResponseDto> listResponse = eawgPage.getContent().stream()
                .map(eawg -> TransactionMapper.mapEntityToDto(eawg, EmployeesAreaWorkgroupResponseDto.class))
                .toList();

        return ResponseEntity.ok(new PageImpl<>(listResponse, pageable, eawgPage.getTotalElements()));
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeesAreaWorkgroupResponseDto> addEawg(@Valid @RequestBody EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto){
        EmployeeAreaWorkgroup eawg = employeeAreaWorkgroupService.createEawg(employeeAreaWorkgroupDto);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(eawg, EmployeesAreaWorkgroupResponseDto.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeesAreaWorkgroupResponseDto> updateEawg(
        @RequestBody EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto, @PathVariable("id") Long id){
        EmployeeAreaWorkgroup employeeAreaWorkgroup = employeeAreaWorkgroupService.updateEawg(id, employeeAreaWorkgroupDto);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup, EmployeesAreaWorkgroupResponseDto.class));
        
    }
}
