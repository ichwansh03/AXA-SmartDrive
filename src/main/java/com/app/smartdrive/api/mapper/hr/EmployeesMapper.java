package com.app.smartdrive.api.mapper.hr;

import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.JobType;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.mapper.TransactionMapper;


public class EmployeesMapper {
    public static EmployeesResponseDto convertEntityToDtoEmp(Employees employees){
        EmployeesResponseDto employeesDto = EmployeesResponseDto.builder()
        .user(TransactionMapper.mapEntityToDto(employees.getUser(), UserDto.class))
        .empName(employees.getEmpName())
        .empJoinDate(employees.getEmpJoinDate())
        .empType(employees.getEmpType())
        .empStatus(employees.getEmpStatus())
        .empGraduate(employees.getEmpGraduate())
        .empNetSalary(employees.getEmpNetSalary())
        .empAccountNumber(employees.getEmpAccountNumber())
        .empModifiedDate(employees.getEmpModifiedDate())
        .jobType(TransactionMapper.mapEntityToDto(employees.getJobType(), EmployeesJobTypeResponseDto.class))
            .build();
        return employeesDto;
    }

    
}
