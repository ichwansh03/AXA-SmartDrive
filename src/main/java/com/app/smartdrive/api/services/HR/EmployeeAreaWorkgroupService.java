package com.app.smartdrive.api.services.HR;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeeAreaWorkgroupService extends BaseService<EmployeeAreaWorkgroup, Long> {
    public EmployeeAreaWorkgroupDto addEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto); 

    public Page<EmployeesAreaWorkgroupResponseDto> searchEawg(String value, int page, int size);

     public EmployeeAreaWorkgroupDto updateEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto, Long id);


      public EmployeeAreaWorkgroup findByEawgById(Long eawg_id);

    

      

}

