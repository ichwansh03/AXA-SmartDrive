package com.app.smartdrive.api.controllers.HR;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.app.smartdrive.api.dto.HR.request.EmployeeSalaryDetailRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeeSalaryDetailResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.mapper.TransactionMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/besa")
public class BatchEmployeeSalaryController {

    private final BatchEmployeeSalaryService batchEmployeeSalaryService;

    @PostMapping("/create/{id}")
    public ResponseEntity<BatchEmployeeSalary> createOne(@PathVariable("id") Long id){
        BatchEmployeeSalary batchEmployeeSalary = batchEmployeeSalaryService.createOne(id);
        return ResponseEntity.ok(batchEmployeeSalary);
    }

    @GetMapping
    public ResponseEntity<List<BatchEmployeeSalary>> getAllTransNull(){
        return ResponseEntity.ok(batchEmployeeSalaryService.getAllTransNull());
    }

    @GetMapping("/commission")
    public ResponseEntity<List<EmployeeSalaryDetailResponseDto>> getAllCommission(
            @RequestBody EmployeeSalaryDetailRequestDto employeeSalaryDetailRequestDto
    ) {
        Long besaEmpEntityId = employeeSalaryDetailRequestDto.getBesaEmpEntityId();
        LocalDate besaCreateDate = employeeSalaryDetailRequestDto.getBesaCreateDate();

        List<EmployeeSalaryDetail> commissionDetails =
                batchEmployeeSalaryService.getAllCommission(besaEmpEntityId, besaCreateDate);

        List<EmployeeSalaryDetailResponseDto> commissionResponseDtoList =
                TransactionMapper.mapEntityListToDtoList(commissionDetails, EmployeeSalaryDetailResponseDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(commissionResponseDtoList);
    }

}
