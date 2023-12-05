package com.app.smartdrive.api.dto.HR.request;

import java.io.Serializable;
import java.util.List;

import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.dto.user.ProfileDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
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
public class CreateEmployeesDto {
    private String empName;
    private String email;
    private String empPhone;
    private String empJoinDate;
    @Enumerated(EnumType.STRING)
    private EnumClassHR.emp_graduate empGraduate;
    private Double empSalary;
    private String empAccountNumber;
    private Long empCity;
    private String empAddress;
    private String empAddress2;
    private String empJobRole;
    private Boolean grantAccessUser;
    // private String empAddress2;
    // private CreateUserDto createUserDto;
    
}
