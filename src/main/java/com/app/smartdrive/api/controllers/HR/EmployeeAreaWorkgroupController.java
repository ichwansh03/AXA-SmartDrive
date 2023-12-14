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

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/eawo")
public class EmployeeAreaWorkgroupController {
        private final EmployeeAreaWorkgroupService employeeAreaWorkgroupService;
    

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

    @GetMapping("/all")
    public List<EmployeesAreaWorkgroupResponseDto> getAllEmployees() {
      return employeeAreaWorkgroupService.getAllDto();
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeesAreaWorkgroupResponseDto> addPartnerAreaWorkGroup(@Valid @RequestBody EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto){

        EmployeeAreaWorkgroup eawg = employeeAreaWorkgroupService.createEawg(employeeAreaWorkgroupDto);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(eawg, EmployeesAreaWorkgroupResponseDto.class));
    }

    @Transactional
    @PutMapping("update/{id}")
    public ResponseEntity<EmployeesAreaWorkgroupResponseDto> updateEawg(
        @RequestBody EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto, @PathVariable("id") Long id){
        EmployeeAreaWorkgroup employeeAreaWorkgroup = employeeAreaWorkgroupService.updateEawg(id, employeeAreaWorkgroupDto);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup, EmployeesAreaWorkgroupResponseDto.class));
        
    }
}
