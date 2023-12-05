package com.app.smartdrive.api.dto.HR.request;

import java.time.LocalDateTime;

import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.hr.EnumClassHR;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesRequest {
    private UserDto user;
    private String empName;
    private LocalDateTime empJoinDate;
    private EnumClassHR.emp_type empType;
    private EnumClassHR.status empStatus;
    private EnumClassHR.emp_graduate empGraduate;
    private Double empNetSalary;
    private String empAccountNumber;
    private LocalDateTime empModifiedDate;
    private EmployeesJobTypeResponseDto empJobCode;
}
