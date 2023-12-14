package com.app.smartdrive.api.controllers.HR;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/eawo")
public class EmployeeAreaWorkgroupController {
    
    private final EmployeeAreaWorkgroupService employeeAreaWorkgroupService;
    
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeeAreaWorkgroupDto> addEmployeeAreaWorkgroup(@RequestBody EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto) {
        
            EmployeeAreaWorkgroupDto resultDto = employeeAreaWorkgroupService.addEmployeeAreaWorkgroup(employeeAreaWorkgroupDto);
            return new ResponseEntity<>(resultDto, HttpStatus.CREATED);       
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<EmployeeAreaWorkgroupDto> updateEmployeeAreaWorkgroup(
            @RequestBody EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto,
            @PathVariable("id") Long id) {
            EmployeeAreaWorkgroupDto updatedDto = employeeAreaWorkgroupService.updateEmployeeAreaWorkgroup(employeeAreaWorkgroupDto, id);
            return ResponseEntity.ok(updatedDto);
        
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> deleteEmployeesById (@PathVariable("id") Long eawg_id) {
            employeeAreaWorkgroupService.deleteById(eawg_id);
            return new ResponseEntity<>("EAWG deleted successfully", HttpStatus.OK);
    }

    
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin')")
    public Page<EmployeesAreaWorkgroupResponseDto> searchEawg(
            @RequestParam(name = "value") String value,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return employeeAreaWorkgroupService.searchEawg(value, page, size);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public List<EmployeesAreaWorkgroupResponseDto> getAllEmployees() {
      return employeeAreaWorkgroupService.getAllDto();
    }
}
