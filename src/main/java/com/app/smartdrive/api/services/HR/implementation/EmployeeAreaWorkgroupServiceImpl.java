package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final EmployeesService employeesService;

    @Override
    @Transactional
    public EmployeeAreaWorkgroup createEawg(EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto){
        EmployeeAreaWorkgroup employeeAreaWorkgroup = new EmployeeAreaWorkgroup();

        AreaWorkGroup areaWorkGroup = arwgService.getById(employeeAreaWorkgroupDto.getArwgCode());
        
        Employees employees = employeesService.getById(employeeAreaWorkgroupDto.getEmpEntityid());

        if (employeeAreaWorkgroupRepository.existsByEmployeesAndAreaWorkGroup(employees, areaWorkGroup)) {
            throw new IllegalStateException("Employee is already associated with the selected area workgroup");
        }

        employeeAreaWorkgroup.setEmployees(employees);
        employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
        employeeAreaWorkgroup.setEawgArwgCode(areaWorkGroup.getArwgCode());
        employeeAreaWorkgroup.setEawgEntityid(employees.getEmpEntityid());
        employeeAreaWorkgroup.setEawgModifiedDate(LocalDateTime.now());
        
        return employeeAreaWorkgroupRepository.save(employeeAreaWorkgroup);
        
    }

    @Override
    @Transactional
    public EmployeeAreaWorkgroup updateEawg(Long eawgId, EmployeeAreaWorkgroupRequestDto employeeAreaWorkgroupDto) {

        EmployeeAreaWorkgroup existingEawg = getById(eawgId);

        if (existingEawg.getEawgArwgCode() != null) {
            AreaWorkGroup areaWorkGroup = arwgService.getById(employeeAreaWorkgroupDto.getArwgCode());
            if (employeeAreaWorkgroupRepository.existsByEmployeesAndAreaWorkGroup(existingEawg.getEmployees(), areaWorkGroup)) {
                throw new IllegalStateException("Employee is already associated with the selected area workgroup");
            }
            existingEawg.setAreaWorkGroup(areaWorkGroup);
            existingEawg.setEawgArwgCode(areaWorkGroup.getArwgCode());
        }

        if (existingEawg.getEawgEntityid() != null) {
            employeeAreaWorkgroupRepository.setEawgEntityid(employeeAreaWorkgroupDto.getEmpEntityid(), existingEawg.getEawgEntityid());
        }

        existingEawg.setEawgModifiedDate(LocalDateTime.now());
        return employeeAreaWorkgroupRepository.save(existingEawg);
    }   

    @Override
    public Page<EmployeeAreaWorkgroup> searchEawg(String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<EmployeeAreaWorkgroup> resultPage = employeeAreaWorkgroupRepository.findeawg(value, value, value, pageable);

        return resultPage;
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
    public Page<EmployeeAreaWorkgroup> getAll(Pageable pageable) {
        return employeeAreaWorkgroupRepository.findAll(pageable);
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
