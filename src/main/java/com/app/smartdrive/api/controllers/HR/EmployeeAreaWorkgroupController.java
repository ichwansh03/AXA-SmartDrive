package com.app.smartdrive.api.controllers.HR;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/eawg")
public class EmployeeAreaWorkgroupController {
        private final EmployeeAreaWorkgroupService employeeAreaWorkgroupService;

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeesAreaWorkgroupResponseDto> addEawg(@Valid @RequestBody EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupRequestDto){
        return ResponseEntity.status(201).body(employeeAreaWorkgroupService.addEawg(employeeAreaWorkgroupRequestDto));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeesAreaWorkgroupResponseDto> updateEawg(
            @RequestBody EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto, @PathVariable Long id){
        return ResponseEntity.status(201).body(employeeAreaWorkgroupService.updateEawg(id,employeeAreaWorkgroupDto));

    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> deleteEmployeesById (@PathVariable Long id) {
            employeeAreaWorkgroupService.deleteById(id);
            return new ResponseEntity<>("EAWG deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/search")
//    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Page<EmployeesAreaWorkgroupResponseDto>> searchEawg(
            @RequestParam(name = "value") String value,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(employeeAreaWorkgroupService.searchEawg(value,page,size));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeesAreaWorkgroupResponseDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(employeeAreaWorkgroupService.getAll(pageable));
    }

}
