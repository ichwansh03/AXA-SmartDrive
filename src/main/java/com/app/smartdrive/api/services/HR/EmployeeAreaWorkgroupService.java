package com.app.smartdrive.api.services.HR;

import java.util.List;


import org.springframework.data.domain.Page;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;

import com.app.smartdrive.api.services.BaseService;
import org.springframework.data.domain.Pageable;

public interface EmployeeAreaWorkgroupService extends BaseService<EmployeeAreaWorkgroup, Long> {
    public Page<EmployeeAreaWorkgroup> searchEawg(String value, int page, int size);

     public EmployeeAreaWorkgroup createEawg(EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto);

     public EmployeeAreaWorkgroup updateEawg(Long eawgId, EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto);

    public Page<EmployeeAreaWorkgroup> getAll(Pageable pageable);



    

      

}

