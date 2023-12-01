package com.app.smartdrive.api.dto.HR.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesJobTypeResponseDto {
    private String jobCode;
    private String jobDesc;
}
