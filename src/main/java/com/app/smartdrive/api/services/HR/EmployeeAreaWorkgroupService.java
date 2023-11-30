package com.app.smartdrive.api.services.HR;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.services.BaseService;

public interface EmployeeAreaWorkgroupService extends BaseService<EmployeeAreaWorkgroup, Long> {
    public EmployeeAreaWorkgroupDto addEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto); 

     public EmployeeAreaWorkgroupDto updateEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto, Long id);

      public List<Object> findByCityNameOrAreaWorkGroupCodeOrEmpName(String searchTerm);

      public EmployeeAreaWorkgroup findByEawgById(Long eawg_id);
      public Page<EmployeeAreaWorkgroup> searchEawg(String value, int page, int size);

      

}

