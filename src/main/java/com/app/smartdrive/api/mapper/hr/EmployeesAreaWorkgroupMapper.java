package com.app.smartdrive.api.mapper.hr;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.mapper.TransactionMapper;

public class EmployeesAreaWorkgroupMapper {
    
     public static EmployeesAreaWorkgroupResponseDto convertEntityToDto(EmployeeAreaWorkgroup employeeAreaWorkgroup){
        EmployeesAreaWorkgroupResponseDto dto = EmployeesAreaWorkgroupResponseDto.builder().areaWorkGroup(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup.getAreaWorkGroup(), ArwgRes.class))
        .employees(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup.getEmployees(), EmployeesDto.class))
        .build();
        return dto;
     }

     public static EmployeeAreaWorkgroupDto converttodto(EmployeeAreaWorkgroup employeeAreaWorkgroup){
        EmployeeAreaWorkgroupDto dto = EmployeeAreaWorkgroupDto.builder()
        .cityId(employeeAreaWorkgroup.getAreaWorkGroup().getArwgCityId())
        .arwgCode(employeeAreaWorkgroup.getEawgArwgCode())
        .empEntityid(employeeAreaWorkgroup.getEmployees().getEmpEntityid())
        .build();
        return dto;
     }
}
