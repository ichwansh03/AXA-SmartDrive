package com.app.smartdrive.api.dto.HR.request;

import java.io.Serializable;

import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;

import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeesDto {
    private UserDto user;
    private String empName;
    private String empJoinDate;
    private String empGraduate;
    private Double empSalary;
    private EmployeesJobTypeResponseDto empJobType;
    // private CreateUserDto createUserDto;
    
}
