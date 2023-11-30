package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.repositories.master.ZonesRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.utils.NullUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeAreaWorkgroupServiceImpl implements EmployeeAreaWorkgroupService {

    private final ArwgRepository areaWorkGroupRepository;
    
    private final EmployeesRepository employeesRepository;

    private final EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;
      
    private final CityRepository cityRepository;

    
    

    @Transactional
    public EmployeeAreaWorkgroupDto addEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        Optional<EmployeeAreaWorkgroup> findTopByOrderByIdDesc = employeeAreaWorkgroupRepository.findLastOptional();
    Long lastIndexUsdr;
    if(findTopByOrderByIdDesc.isPresent()){
       lastIndexUsdr = findTopByOrderByIdDesc.get().getEawgId();
    } else {
      lastIndexUsdr = 1L;
    }
    
        Employees existingEmployee = employeesRepository.findByEmpName(employeeAreaWorkgroupDto.getEmpName());
           
        EmployeeAreaWorkgroup employeeAreaWorkgroup = new EmployeeAreaWorkgroup();
        EmployeeAreaWorkgroupId employeeAreaWorkgroupId = new EmployeeAreaWorkgroupId();
        employeeAreaWorkgroupId.setEawgEntityid(existingEmployee.getEmpEntityid());
        employeeAreaWorkgroup.setEawgId(lastIndexUsdr+1);
        employeeAreaWorkgroup.setEmployees(existingEmployee);   
        employeeAreaWorkgroup.setEmployeeAreaWorkgroupId(employeeAreaWorkgroupId);
        

        if (employeeAreaWorkgroupDto.getCityName() != null) {
            Cities city = cityRepository.findByCityName(employeeAreaWorkgroupDto.getCityName());
            if (city != null) {
                Provinsi provinsi = city.getProvinsi();
                String provName = provinsi.getProvName();
                Zones zones = provinsi.getZones();
                String zonesName = zones.getZonesName();
    
                // Set additional properties to DTO
                employeeAreaWorkgroupDto.setProvinsi(provName);
                employeeAreaWorkgroupDto.setZoneName(zonesName);
                employeeAreaWorkgroupDto.setCityName(city.getCityName());
                
    
                AreaWorkGroup areaWorkGroup = areaWorkGroupRepository.findById(employeeAreaWorkgroupDto.getAreaworkGroup()).orElse(null);
                if (areaWorkGroup != null) {
                    employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
                    employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
                }
                Cities cityInAreaWorkGroup = areaWorkGroup.getCities();
                employeeAreaWorkgroupDto.setCityName(cityInAreaWorkGroup.getCityName());
            }
        }

        employeeAreaWorkgroup.setEawgModifiedDate(LocalDateTime.now());
        employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);

        employeeAreaWorkgroupDto.setEmpName(employeeAreaWorkgroupDto.getEmpName());
        employeeAreaWorkgroupDto.setAreaworkGroup(employeeAreaWorkgroupDto.getAreaworkGroup());
        employeeAreaWorkgroupDto.setCityName(employeeAreaWorkgroupDto.getCityName());
        employeeAreaWorkgroupDto.setProvinsi(employeeAreaWorkgroupDto.getProvinsi());
        employeeAreaWorkgroupDto.setZoneName(employeeAreaWorkgroupDto.getZoneName());

        return employeeAreaWorkgroupDto;
    }
     
    @Override
    @Transactional
    public EmployeeAreaWorkgroupDto updateEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto, Long id) {
        Cities cities = cityRepository.findByCityName(employeeAreaWorkgroupDto.getCityName());
        Provinsi provinsi = cities.getProvinsi();
                String provName = provinsi.getProvName();
                Zones zones = provinsi.getZones();
                String zonesName = zones.getZonesName();
                employeeAreaWorkgroupDto.setProvinsi(provName);
                employeeAreaWorkgroupDto.setZoneName(zonesName);
                employeeAreaWorkgroupDto.setCityName(cities.getCityName());

        List<AreaWorkGroup> listAreaWorkGroup = cities.getAreaWorkGroups();
        AreaWorkGroup areaWorkGroup= areaWorkGroupRepository.findByArwgCode(employeeAreaWorkgroupDto.getAreaworkGroup());
        for (AreaWorkGroup arwg : listAreaWorkGroup) {
            if(arwg.getArwgCode().equals(employeeAreaWorkgroupDto.getAreaworkGroup())){
                areaWorkGroup = arwg;
            }
        }
        EmployeeAreaWorkgroup employeeAreaWorkgroup = employeeAreaWorkgroupRepository.findByEawgId(id).get();
        Employees employees = employeesRepository.findByEmpName(employeeAreaWorkgroupDto.getEmpName());
            NullUtils.updateIfChanged(employeeAreaWorkgroup::setEawgArwgCode, employeeAreaWorkgroupDto.getAreaworkGroup(),employeeAreaWorkgroup::getEawgArwgCode);
            NullUtils.updateIfChanged(employees::setEmpName, employeeAreaWorkgroupDto.getEmpName(), employees::getEmpName);

            employeeAreaWorkgroup.setEawgModifiedDate(LocalDateTime.now());

            employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
            employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
            employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);


    return employeeAreaWorkgroupDto;
    
    }

    @Override
    public List<Object> findByCityNameOrAreaWorkGroupCodeOrEmpName(String searchTerm) {
        List<Cities> citiesResult = cityRepository.findByCityNameIgnoreCase(searchTerm);
        List<AreaWorkGroup> areaWorkGroupResult = areaWorkGroupRepository.findByArwgCodeIgnoreCase(searchTerm);
        List<Employees> employeesResult = employeesRepository.findAllByEmpName(searchTerm);

    
        return List.of(citiesResult, areaWorkGroupResult, employeesResult);
    }

    @Override
    public Page<EmployeeAreaWorkgroup> searchEawg(String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        return employeeAreaWorkgroupRepository.findByEawgArwgCodeOrEmployees_EmpNameContainingOrAreaWorkGroup_Cities_CityNameContaining(value, value, value, pageable);
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
    public void deleteById(Long eawg_id) {
        // TODO Auto-generated method stub
         employeeAreaWorkgroupRepository.deleteEawgById(eawg_id);
    }

    public EmployeeAreaWorkgroup findByEawgById(Long eawg_id){
        Optional<EmployeeAreaWorkgroup> findByEawgId = employeeAreaWorkgroupRepository.findByEawgId(eawg_id);
        return findByEawgId.get();
    }
    
}
