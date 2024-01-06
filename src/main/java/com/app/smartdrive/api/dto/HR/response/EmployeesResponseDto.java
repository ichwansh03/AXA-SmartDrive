package com.app.smartdrive.api.dto.HR.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.hr.EnumClassHR;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesResponseDto {
    private UserDto user;
    private Long empEntityid;
    private String empName;
    @JsonFormat(pattern = "dd-MMMM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDateTime empJoinDate;
    private EnumClassHR.emp_type empType;
    private EnumClassHR.status empStatus;
    private EnumClassHR.emp_graduate empGraduate;
    private BigDecimal empNetSalary;
    private String empAccountNumber;
    private LocalDateTime empModifiedDate;
    private EmployeesJobTypeResponseDto jobType;
}
