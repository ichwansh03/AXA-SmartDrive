package com.app.smartdrive.api.dto.HR.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSalaryDetailRequestDto {
    private Long besaEmpEntityId;
    private LocalDate besaCreateDate;
}
