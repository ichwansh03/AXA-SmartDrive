package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.repositories.master.ZonesRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.users.BusinessEntityService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeAreaWorkgroupServiceImpl implements EmployeeAreaWorkgroupService {
    private final BusinessEntityService businessEntityService;

    private final ArwgRepository areaWorkGroupRepository;
    
    private final EmployeesRepository employeesRepository;
    
    private final EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;
      
    private final CityRepository cityRepository;
    
    

    @Transactional
    public EmployeeAreaWorkgroupDto addEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        // Save the business entity and get the ID
        Long businessEntityId = businessEntityService.save(businessEntity);


        Employees existingEmployee = employeesRepository.findByEmpName(employeeAreaWorkgroupDto.getEmpName());
        Employees employee;

        if (existingEmployee != null) {
            employee = existingEmployee;
        } else {

            employee = new Employees();
            employee.setEmpEntityid(businessEntityId);
            employee.setEmpName(employeeAreaWorkgroupDto.getEmpName());
        }

        // Save or update employee
        employeesRepository.save(employee);

        EmployeeAreaWorkgroup employeeAreaWorkgroup = new EmployeeAreaWorkgroup();
//        employeeAreaWorkgroup.setEawgId(businessEntityId + 2);
//        employeeAreaWorkgroup.setEawgEntityid(employee.getEmpEntityid());
        employeeAreaWorkgroup.setEawgArwgCode(employeeAreaWorkgroupDto.getWorkGroup());

        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();

        if (employeeAreaWorkgroupDto.getCityName() != null) {
            Cities city = cityRepository.findByCityName(employeeAreaWorkgroupDto.getCityName());
            if (city != null) {
                Provinsi provinsi = city.getProvinsi();
                String provName = provinsi.getProvName();
                Zones zones = provinsi.getZones();
                String zonesName = zones.getZonesName();

                employeeAreaWorkgroupDto.setProvinsi(provName);
                employeeAreaWorkgroupDto.setZoneName(zonesName);
                employeeAreaWorkgroupDto.setCityName(city.getCityName());

                areaWorkGroup = areaWorkGroupRepository.findById(employeeAreaWorkgroupDto.getWorkGroup())
                        .orElse(null);
                if (areaWorkGroup != null) {
                    employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
                    employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
                }
            }
        }

        employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);

        employeeAreaWorkgroupDto.setEmpName(employeeAreaWorkgroupDto.getEmpName());
        employeeAreaWorkgroupDto.setWorkGroup(employeeAreaWorkgroupDto.getWorkGroup());
        employeeAreaWorkgroupDto.setCityName(employeeAreaWorkgroupDto.getCityName());
        employeeAreaWorkgroupDto.setProvinsi(employeeAreaWorkgroupDto.getProvinsi());
        employeeAreaWorkgroupDto.setZoneName(employeeAreaWorkgroupDto.getZoneName());

        return employeeAreaWorkgroupDto;
    }
     
    
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
