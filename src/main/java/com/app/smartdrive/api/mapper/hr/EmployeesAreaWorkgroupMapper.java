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
    
      public static EmployeesAreaWorkgroupResponseDto convertToDto(EmployeeAreaWorkgroup employeeAreaWorkgroup) {
        EmployeesAreaWorkgroupResponseDto dto = new EmployeesAreaWorkgroupResponseDto();
        dto.setAreaWorkGroup(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup.getAreaWorkGroup(), ArwgRes.class));
        dto.setEmployees(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup.getEmployees(), EmployeesDto.class));
        return dto;
    }

}
