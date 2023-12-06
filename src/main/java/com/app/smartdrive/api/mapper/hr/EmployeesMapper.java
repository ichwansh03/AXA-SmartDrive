package com.app.smartdrive.api.mapper.hr;

import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.JobType;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.mapper.TransactionMapper;


public class EmployeesMapper {
    public static EmployeesDto convertEntityToDto(Employees employees){
        EmployeesDto employeesDto = EmployeesDto.builder()
        .user(TransactionMapper.mapEntityToDto(employees.getUser(), UserDto.class))
        .empName(employees.getEmpName())
        .empJoinDate(employees.getEmpJoinDate())
        .empType(employees.getEmpType())
        .empStatus(employees.getEmpStatus())
        .empGraduate(employees.getEmpGraduate())
        .empNetSalary(employees.getEmpNetSalary())
        .empAccountNumber(employees.getEmpAccountNumber())
        .empModifiedDate(employees.getEmpModifiedDate())
        .empJobCode(employees.getEmpJobCode())
            .build();
        return employeesDto;
    }

    
}
