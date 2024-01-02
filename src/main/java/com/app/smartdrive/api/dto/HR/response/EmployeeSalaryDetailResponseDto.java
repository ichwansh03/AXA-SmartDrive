package com.app.smartdrive.api.dto.HR.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalaryDetailResponseDto {
    private String emsaName;
    private BigDecimal emsaSubtotal;
    private EmpDetailResponseDto employees;
}
