package com.app.smartdrive.api.controllers.HR;

import java.util.List;

import com.app.smartdrive.api.dto.HR.request.EmployeeSalaryDetailRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeeSalaryDetailResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/besa")
@CrossOrigin
public class BatchEmployeeSalaryController {

    private final BatchEmployeeSalaryService batchEmployeeSalaryService;

    @PostMapping("/create/{id}")
    public ResponseEntity<BatchEmployeeSalary> createOne(@PathVariable("id") Long id){
        BatchEmployeeSalary batchEmployeeSalary = batchEmployeeSalaryService.createOneWithDetails(id);
        return ResponseEntity.ok(batchEmployeeSalary);
    }

    @GetMapping
    public ResponseEntity<?> getAllTransNull(){
        return ResponseEntity.ok(batchEmployeeSalaryService.getAllTransNull());
    }

    @GetMapping("/commission")
    public ResponseEntity<List<EmployeeSalaryDetailResponseDto>> getAllCommission(
            @RequestBody EmployeeSalaryDetailRequestDto employeeSalaryDetailRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(batchEmployeeSalaryService.getAllCommission(employeeSalaryDetailRequestDto.getBesaEmpEntityId(),employeeSalaryDetailRequestDto.getBesaCreateDate()));
    }



}
