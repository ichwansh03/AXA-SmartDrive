package com.app.smartdrive.api.services.HR;

import java.util.List;


import org.springframework.data.domain.Page;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;

import com.app.smartdrive.api.services.BaseService;

public interface EmployeeAreaWorkgroupService extends BaseService<EmployeeAreaWorkgroup, Long> {
    public Page<EmployeesAreaWorkgroupResponseDto> searchEawg(String value, int page, int size);

     public EmployeeAreaWorkgroup createEawg(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto);

     public EmployeeAreaWorkgroup updateEawg(Long eawgId, EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto);

     public List<EmployeesAreaWorkgroupResponseDto> getAllDto();



    

      

}

