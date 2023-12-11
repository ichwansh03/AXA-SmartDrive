package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.master.Zones;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.users.BusinessEntity;

import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.master.ProvRepository;
import com.app.smartdrive.api.repositories.master.ZonesRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.master.ArwgService;
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
 
    

    @Override
    @Transactional
    public EmployeeAreaWorkgroupDto addEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto) {
        Employees existingEmployee = employeesRepository.findById(employeeAreaWorkgroupDto.getEmpEntityid()).orElse(null);
    
        EmployeeAreaWorkgroup employeeAreaWorkgroup = new EmployeeAreaWorkgroup();
    
        employeeAreaWorkgroup.setEawgEntityid(existingEmployee.getEmpEntityid());
        employeeAreaWorkgroup.setEmployees(existingEmployee);
    
        if (employeeAreaWorkgroupDto.getCityId() != null) {
            Cities city = cityRepository.findById(employeeAreaWorkgroupDto.getCityId()).orElse(null);
    
            if (city != null) {
                Provinsi provinsi = city.getProvinsi();
                String provName = provinsi.getProvName();
                Zones zones = provinsi.getZones();
                String zonesName = zones.getZonesName();
                
                employeeAreaWorkgroupDto.setProvinsi(provName);
                employeeAreaWorkgroupDto.setZoneName(zonesName);
                employeeAreaWorkgroupDto.setCityId(city.getCityId());
    
                AreaWorkGroup areaWorkGroup = areaWorkGroupRepository.findById(employeeAreaWorkgroupDto.getArwgCode()).orElse(null);
                if (areaWorkGroup != null) {
                    employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
//                    employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
                }
                Cities cityInAreaWorkGroup = areaWorkGroup != null ? areaWorkGroup.getCities() : null;
                if (cityInAreaWorkGroup != null) {
                    employeeAreaWorkgroupDto.setCityId(cityInAreaWorkGroup.getCityId());
                }
            }
        }
    
        employeeAreaWorkgroup.setEawgModifiedDate(LocalDateTime.now());
        employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);

        return employeeAreaWorkgroupDto;
    }
    
    @Override
    @Transactional
    public EmployeeAreaWorkgroupDto updateEmployeeAreaWorkgroup(EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto, Long id) {
        Optional<EmployeeAreaWorkgroup> optionalEmployeeAreaWorkgroup = employeeAreaWorkgroupRepository.findByEawgId(id);
        if (optionalEmployeeAreaWorkgroup.isPresent()) {
            EmployeeAreaWorkgroup employeeAreaWorkgroup = optionalEmployeeAreaWorkgroup.get();

//            if (!Objects.equals(employeeAreaWorkgroupDto.getArwgCode(), employeeAreaWorkgroup.getEawgArwgCode())) {
                AreaWorkGroup areaWorkGroup = areaWorkGroupRepository.findByArwgCode(employeeAreaWorkgroupDto.getArwgCode());
                if (areaWorkGroup != null) {
                    employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
//                    employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
                }
//            }

            if (!Objects.equals(employeeAreaWorkgroupDto.getEmpEntityid(), employeeAreaWorkgroup.getEawgEntityid())) {
                Employees employees = employeesRepository.findById(employeeAreaWorkgroupDto.getEmpEntityid()).orElse(null);
                if (employees != null) {
                    employeeAreaWorkgroup.setEawgEntityid(employeeAreaWorkgroupDto.getEmpEntityid());
                }
            }
            employeeAreaWorkgroup.setEawgModifiedDate(LocalDateTime.now());
            employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);
        }

            return employeeAreaWorkgroupDto;
        }

   

    @Override
    public Page<EmployeesAreaWorkgroupResponseDto> searchEawg(String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

//        Page<EmployeeAreaWorkgroup> resultPage = employeeAreaWorkgroupRepository.findByEawgArwgCodeOrEmployees_EmpNameContainingOrAreaWorkGroup_Cities_CityNameContaining(value, value, value, pageable);

        Page<EmployeeAreaWorkgroup> resultPage = employeeAreaWorkgroupRepository.findAll(pageable);

        List<EmployeesAreaWorkgroupResponseDto> dtos = resultPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, resultPage.getTotalElements());
    }

    private EmployeesAreaWorkgroupResponseDto convertToDto(EmployeeAreaWorkgroup employeeAreaWorkgroup) {
        EmployeesAreaWorkgroupResponseDto dto = new EmployeesAreaWorkgroupResponseDto();
        dto.setAreaWorkGroup(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup.getAreaWorkGroup(), ArwgRes.class));
        dto.setEmployees(TransactionMapper.mapEntityToDto(employeeAreaWorkgroup.getEmployees(), EmployeesDto.class));
        return dto;
    }

    @Override
    public List<EmployeesAreaWorkgroupResponseDto> getAllDto() {
      List<EmployeeAreaWorkgroup> employeeAreaWorkgroups = employeeAreaWorkgroupRepository.findAll();
      List<EmployeesAreaWorkgroupResponseDto> empDto = TransactionMapper.mapEntityListToDtoList(employeeAreaWorkgroups, EmployeesAreaWorkgroupResponseDto.class);
      return empDto;
    }
    
    
    @Override
    public EmployeeAreaWorkgroup getById(Long id) {
        // TODO Auto-generated method stub
        return null;
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


    
}
