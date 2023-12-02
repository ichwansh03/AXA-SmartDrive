package com.app.smartdrive.api.dto.HR.response;

import java.io.Serializable;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeesDto extends CreateUserDto{
    private String empName;
    private String empJoinDate;
    private String empGraduate;
    private Double empSalary;
    private String empAccountNumber;
    private EmployeesJobTypeResponseDto empJobType;
    private UserDto user;
    // private CreateUserDto createUserDto;

    
    
}
