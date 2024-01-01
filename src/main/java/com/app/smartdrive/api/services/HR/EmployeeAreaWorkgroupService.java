package com.app.smartdrive.api.services.HR;

import java.util.List;


import org.springframework.data.domain.Page;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;

import com.app.smartdrive.api.services.BaseService;
import org.springframework.data.domain.Pageable;

public interface EmployeeAreaWorkgroupService{
     Page<EmployeeAreaWorkgroup> searchEawg(String value, int page, int size);
     EmployeeAreaWorkgroup createEawg(EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto);
     EmployeeAreaWorkgroup updateEawg(Long eawgId, EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto);
     EmployeeAreaWorkgroup getById(Long id);
     void deleteById(Long eawg_id);
     Page<EmployeeAreaWorkgroup> getAll(Pageable pageable);

     EmployeeAreaWorkgroup getById(Long eawgId, Long employeeId);


    

      

}

