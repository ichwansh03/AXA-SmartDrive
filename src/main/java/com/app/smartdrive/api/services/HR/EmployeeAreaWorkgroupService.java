package com.app.smartdrive.api.services.HR;


import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import org.springframework.data.domain.Page;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupRequestDto;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;

import org.springframework.data.domain.Pageable;

public interface EmployeeAreaWorkgroupService{
     Page<EmployeesAreaWorkgroupResponseDto> searchEawg(String value, int page, int size);
     EmployeesAreaWorkgroupResponseDto addEawg(EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto);
     EmployeesAreaWorkgroupResponseDto updateEawg (Long eawgId, EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto);
     EmployeeAreaWorkgroup getById(Long id);
     void deleteById(Long eawg_id);
     Page<EmployeesAreaWorkgroupResponseDto> getAll(Pageable pageable);

     EmployeeAreaWorkgroup getById(Long eawgId, Long employeeId);


    

      

}

