package com.app.smartdrive.api.dto.HR.request;



import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;

import com.app.smartdrive.api.entities.hr.EnumClassHR;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesRequestDto {
    private String empName;
    private String email;
    private UserPhoneRequestDto empPhone;
    private String empJoinDate;
    @Enumerated(EnumType.STRING)
    private EnumClassHR.emp_graduate empGraduate;
    private Double empSalary;
    private String empAccountNumber;
    private UserAddressRequestDto empAddress;
    private String empJobRole;
    private Boolean grantAccessUser;
    // private String empAddress2;
    // private CreateUserDto createUserDto;
    
}
