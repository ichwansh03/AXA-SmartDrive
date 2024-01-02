package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;

import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.services.master.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.HR.request.EmployeeAreaWorkgroupRequestDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.master.ArwgService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeAreaWorkgroupServiceImpl implements EmployeeAreaWorkgroupService {

    private final EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;

    private final ArwgService arwgService;

    private final CityService cityService;

    private final EmployeesService employeesService;

    @Override
    @Transactional
    public EmployeesAreaWorkgroupResponseDto addEawg(EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto) {
        AreaWorkGroup areaWorkGroup = arwgService.getById(employeeAreaWorkgroupDto.getArwgCode());
        Employees employees = employeesService.getById(employeeAreaWorkgroupDto.getEmpEntityid());

        Cities cities = cityService.getById(employeeAreaWorkgroupDto.getCityId());

        validateNotExist(employees, areaWorkGroup, cities);

        EmployeeAreaWorkgroup employeeAreaWorkgroup = createEmployeeAreaWorkgroup(areaWorkGroup, employees);

        EmployeeAreaWorkgroup saveEawg = employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);

        return TransactionMapper.mapEntityToDto(saveEawg, EmployeesAreaWorkgroupResponseDto.class);
    }

    private void validateNotExist(Employees employees, AreaWorkGroup areaWorkGroup, Cities cities) {
        if (employeeAreaWorkgroupRepository.existsByEmployeesAndAreaWorkGroup(employees, areaWorkGroup)) {
            throw new IllegalStateException("Employee is already associated with the selected area workgroup");
        }

        if (!areaWorkGroup.getCities().equals(cities)) {
            throw new IllegalStateException("AreaWorkGroup is not associated with the specified City");
        }
    }

    private EmployeeAreaWorkgroup createEmployeeAreaWorkgroup(AreaWorkGroup areaWorkGroup, Employees employees) {
        EmployeeAreaWorkgroup employeeAreaWorkgroup = new EmployeeAreaWorkgroup();
        employeeAreaWorkgroup.setEmployees(employees);
        employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
        employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
        employeeAreaWorkgroup.setEawgEntityid(employees.getEmpEntityid());
        employeeAreaWorkgroup.setEawgModifiedDate(LocalDateTime.now());
        return employeeAreaWorkgroup;
    }


    @Override
    @Transactional
    public EmployeesAreaWorkgroupResponseDto updateEawg(Long eawgId, EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto) {
        EmployeeAreaWorkgroup existingEawg = getById(eawgId);
        AreaWorkGroup areaWorkGroup = arwgService.getById(employeeAreaWorkgroupDto.getArwgCode());
        Employees employees = employeesService.getById(employeeAreaWorkgroupDto.getEmpEntityid());

        Cities cities = cityService.getById(employeeAreaWorkgroupDto.getCityId());

        validateUpdate(existingEawg,areaWorkGroup,employees,cities);
        setEawg(existingEawg,areaWorkGroup,employees);

        existingEawg.setEawgModifiedDate(LocalDateTime.now());

        EmployeeAreaWorkgroup saveEawg = employeeAreaWorkgroupRepository.save(existingEawg);

        return TransactionMapper.mapEntityToDto(saveEawg, EmployeesAreaWorkgroupResponseDto.class);
    }

    private void validateUpdate(EmployeeAreaWorkgroup existingEawg, AreaWorkGroup newAreaWorkGroup, Employees employee, Cities newCities) {
        if (!newAreaWorkGroup.getCities().equals(newCities)) {
            throw new IllegalStateException("AreaWorkGroup is not associated with the specified City");
        }

        if (!existingEawg.getAreaWorkGroup().equals(newAreaWorkGroup) &&
                employeeAreaWorkgroupRepository.existsByEmployeesAndAreaWorkGroup(employee, newAreaWorkGroup)) {
            throw new IllegalStateException("Employee is already associated with the selected area workgroup");
        }
    }

    private void setEawg(EmployeeAreaWorkgroup existingEawg, AreaWorkGroup newAreaWorkGroup, Employees employee) {
        existingEawg.setAreaWorkGroup(newAreaWorkGroup);
        existingEawg.setEawgArwgCode(newAreaWorkGroup.getArwgCode());
        existingEawg.setEawgEntityid(employee.getEmpEntityid());
        existingEawg.setEawgModifiedDate(LocalDateTime.now());
    }

    @Override
    public Page<EmployeesAreaWorkgroupResponseDto> searchEawg(String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<EmployeeAreaWorkgroup> eawgPage = employeeAreaWorkgroupRepository.findeawg(value, value, value, pageable);

        List<EmployeesAreaWorkgroupResponseDto> listResponse = eawgPage.getContent().stream()
                .map(eawg -> TransactionMapper.mapEntityToDto(eawg, EmployeesAreaWorkgroupResponseDto.class))
                .toList();

        return new PageImpl<>(listResponse, pageable, eawgPage.getTotalElements());
    }
    
    @Override
    @Transactional(readOnly = true)
    public EmployeeAreaWorkgroup getById(Long id) {
        return this.employeeAreaWorkgroupRepository.findByEawgId(id).orElseThrow(
                () -> new EntityNotFoundException("EmployeeAreaWorkgroup is not found")
        );
    }

    @Override
    @Transactional
    public Page<EmployeesAreaWorkgroupResponseDto> getAll(Pageable pageable) {
        Page<EmployeeAreaWorkgroup> eawgPage = employeeAreaWorkgroupRepository.findAll(pageable);

        List<EmployeesAreaWorkgroupResponseDto> listResponse = eawgPage.getContent().stream()
                .map(eawg -> TransactionMapper.mapEntityToDto(eawg, EmployeesAreaWorkgroupResponseDto.class))
                .toList();

        return new PageImpl<>(listResponse, pageable, eawgPage.getTotalElements());
    }

    @Transactional
    @Override
    public EmployeeAreaWorkgroup getById(Long eawgId, Long employeeId) {

        return this.employeeAreaWorkgroupRepository.findById(new EmployeeAreaWorkgroupId(eawgId, employeeId))
                .orElseThrow(() -> new EntityNotFoundException("Employee Areaworkgroup with id " + eawgId + " is not found"));
    }

    @Override
    public void deleteById(Long eawg_id) {
         employeeAreaWorkgroupRepository.deleteEawgById(eawg_id);
    }


    
}
