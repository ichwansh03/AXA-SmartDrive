package com.app.smartdrive.api.dto.HR.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BatchEmployeeSalaryResponseDto {
    private EmployeesResponseDto employees;
    private LocalDate besaCreatedDate;
    private LocalDateTime besaPaidDate;
    private String besaPatrTrxno;
}
