package com.app.smartdrive.api.services.HR.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.master.AreaWorkGroupRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.repositories.master.ZonesRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.partner.PartnerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeAreaWorkgroupServiceImpl implements EmployeeAreaWorkgroupService {
    private AreaWorkGroupRepository areaWorkGroupRepository;

    private EmployeesRepository employeesRepository;
    
    private EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;
      
    private CityRepository cityRepository;
    
    private ZonesRepository zonaRepository;

    private ProvRepository provinsiRepository;

   
    
    
    
    
    @Override
    public EmployeeAreaWorkgroup getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<EmployeeAreaWorkgroup> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public EmployeeAreaWorkgroup save(EmployeeAreaWorkgroup entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }
    
}
