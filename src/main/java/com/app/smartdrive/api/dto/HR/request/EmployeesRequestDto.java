package com.app.smartdrive.api.dto.HR.request;

import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;

import com.app.smartdrive.api.entities.hr.EnumClassHR;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesRequestDto {
    @Size(max = 85)
    private String empName;
    @Size(max = 25)
    private String email;
    private String userBirthPlace;
    private UserPhoneRequestDto empPhone;
    private String empJoinDate;
    @Enumerated(EnumType.STRING)
    private EnumClassHR.emp_graduate empGraduate;
    private BigDecimal empSalary;
    @Size(max = 30)
    private String empAccountNumber;
    private UserAddressRequestDto empAddress;
    private String jobType;
    private Boolean grantAccessUser;

    
}
