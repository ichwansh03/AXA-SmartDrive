package com.app.smartdrive.api.controllers.HR;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;

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

     @PutMapping("update/{id}")
    public ResponseEntity<EmployeeAreaWorkgroupDto> updateEmployeeAreaWorkgroup(
            @ModelAttribute EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto,
            @PathVariable("id") Long id) {
        
            EmployeeAreaWorkgroupDto updatedDto = employeeAreaWorkgroupService.updateEmployeeAreaWorkgroup(employeeAreaWorkgroupDto, id);
            return ResponseEntity.ok(updatedDto);
        
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployeesById (@RequestParam Long eawg_id) {
            employeeAreaWorkgroupService.deleteById(eawg_id);
            return new ResponseEntity<>("EAWG deleted successfully", HttpStatus.OK);
    }

    
    @GetMapping("/search")
    public Page<EmployeesAreaWorkgroupResponseDto> searchEawg(
            @RequestParam(name = "value") String value,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return employeeAreaWorkgroupService.searchEawg(value, page, size);
    }

}
