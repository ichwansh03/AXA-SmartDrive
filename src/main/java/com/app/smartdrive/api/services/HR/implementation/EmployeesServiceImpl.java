package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.entities.users.EnumUsers.roleName;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserAddressRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;
import com.app.smartdrive.api.utils.NullUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {
    
    private final BusinessEntityService businessEntityService;

    private final EmployeesRepository employeesRepository;

    private final RolesRepository rolesRepository;

    private final UserRepository userRepository;

    private final UserServiceImpl userServiceImpl;
    
    private final UserPhoneRepository userPhoneRepository;

    private final UserAddressRepository userAddressRepository;

    private final CityRepository cityRepository;


    @Override
    @Transactional
    public EmployeesDto addEmployee(EmployeesDto employeesDto) {
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());
    
        // Optional<UserAddress> findTopByOrderByIdDesc = userAddressRepository.findLastOptional();
        // Long lastIndexUsdr;
        // if(findTopByOrderByIdDesc.isPresent()){
        // lastIndexUsdr = findTopByOrderByIdDesc.get().getUserAdressId().getUsdrId();
        // } else {
        // lastIndexUsdr = 1L;
        // }

        User user = new User();
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setEntityModifiedDate(LocalDateTime.now());

        // Save the business entity and get the ID
        Long businessEntityId = businessEntityService.save(businessEntity); 
        
        // if(employeesDto.getGrantUserAccess()){
        user.setUserBusinessEntity(businessEntity);
        user.setUserEntityId(businessEntityId);
        user.setUserName(employeesDto.getEmpName()+businessEntityId);
        user.setUserFullName(employeesDto.getEmpName());
        user.setUserEmail(employeesDto.getEmpEmail());
        user.setUserModifiedDate(LocalDateTime.now());
        user.setUserNationalId("idnn" + businessEntityId);
        user.setUserNPWP("npwp" + businessEntityId);
    
        
        UserRolesId userRolesId = new UserRolesId(businessEntityId, roleName.EM);
        Roles roles = rolesRepository.findById(roleName.EM).get();
        
        UserRoles userRoles = new UserRoles();
        userRoles.setUserRolesId(userRolesId);
        userRoles.setRoles(roles);
        userRoles.setUsroStatus("ACTIVE");
        userRoles.setUsroModifiedDate(LocalDateTime.now());
        userRoles.setUser(user);
        
        List<UserRoles> listRole = List.of(userRoles);
        
        UserPhone userPhone = new UserPhone();
        UserPhoneId userPhoneId = new UserPhoneId(businessEntityId, employeesDto.getEmpPhone());
        userPhone.setUserPhoneId(userPhoneId);
        userPhone.setUsphPhoneType("HP");
        userPhone.setUsphModifiedDate(LocalDateTime.now());
        userPhone.setUser(user);
        List<UserPhone> listUserPhones = List.of(userPhone);
    
        Cities empCityEntity = cityRepository.findByCityName(employeesDto.getEmpCity());
    
        UserAddress userAddress = new UserAddress();
        userAddress.setUsdrEntityId(businessEntityId);
        userAddress.setUsdrAddress1(employeesDto.getEmpAddress());
        userAddress.setUsdrAdress2(employeesDto.getEmpAddress2());
        userAddress.setUser(user);
        userAddress.setCity(empCityEntity);
        
        List<UserAddress> listuAddresses = List.of(userAddress);
        
        
        
        // userPhoneRepository.save(userPhone);
        // userAddressRepository.save(userAddress);
        // }
        
        // double commissionPercentage = 0.10; // 10%
        // double premiumAmount = 1000000.0; // Example premium amount
        
        // double commission = commissionPercentage * premiumAmount;
        
        Employees employee = new Employees();
        employee.setEmpEntityid(businessEntityId);
        employee.setEmpName(employeesDto.getEmpName());
        employee.setEmpJoinDate(empJoinDate);
        employee.setEmpGraduate(EnumClassHR.emp_graduate.valueOf(employeesDto.getEmpGraduate()));
        employee.setEmpStatus(EnumClassHR.status.ACTIVE);
        employee.setEmpNetSalary(employeesDto.getEmpSalary());
        employee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
        employee.setUser(user);
        employee.setEmpJobCode(employeesDto.getEmpRole());

        user.setUserRoles(listRole);
        user.setUserPhone(listUserPhones);
        user.setUserAddress(listuAddresses);

        // userServiceImpl.save(user);


        employeesRepository.save(employee);

        employeesDto.setEmpName(employeesDto.getEmpName());
        employeesDto.setEmpEmail(employeesDto.getEmpEmail());
        employeesDto.setEmpPhone(employeesDto.getEmpPhone());
        employeesDto.setEmpJoinDate(employeesDto.getEmpJoinDate());
        employeesDto.setEmpGraduate(employeesDto.getEmpGraduate());
        employeesDto.setEmpRole(employeesDto.getEmpRole());
        employeesDto.setEmpSalary(employeesDto.getEmpSalary());
        employeesDto.setEmpCity(employeesDto.getEmpCity());
        employeesDto.setEmpAddress(employeesDto.getEmpAddress());
        employeesDto.setEmpAddress2(employeesDto.getEmpAddress2());

    
        return employeesDto;
    }

    @Override
    @Transactional
    public EmployeesDto updateEmployee(Long employeeId, EmployeesDto updatedEmployeeDto) {
        // Retrieve the existing employee entity from the database
        Employees existingEmployee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        User user = existingEmployee.getUser(); 
        
        UserAddress userAddress = userAddressRepository.findByUserUserEntityId(user.getUserEntityId());
        userAddress.setCity(cityRepository.findByCityName(updatedEmployeeDto.getEmpCity()));

        LocalDateTime empJoinDate = LocalDateTime.parse(updatedEmployeeDto.getEmpJoinDate());
        EnumClassHR.emp_graduate empGraduate = EnumClassHR.emp_graduate.valueOf(updatedEmployeeDto.getEmpGraduate());
        NullUtils.updateIfChanged(existingEmployee::setEmpName, updatedEmployeeDto.getEmpName(), existingEmployee::getEmpName);
        NullUtils.updateIfChanged(existingEmployee::setEmpJoinDate,empJoinDate,existingEmployee::getEmpJoinDate);
        NullUtils.updateIfChanged(existingEmployee::setEmpGraduate, empGraduate, existingEmployee::getEmpGraduate);
        NullUtils.updateIfChanged(existingEmployee::setEmpNetSalary, updatedEmployeeDto.getEmpSalary(), existingEmployee::getEmpNetSalary);
        NullUtils.updateIfChanged(existingEmployee::setEmpAccountNumber, updatedEmployeeDto.getEmpPhone(), existingEmployee::getEmpAccountNumber);
        NullUtils.updateIfChanged(user::setUserEmail, updatedEmployeeDto.getEmpEmail(), user::getUserEmail);
        NullUtils.updateIfChanged(userAddress::setUsdrAddress1, updatedEmployeeDto.getEmpAddress(), userAddress::getUsdrAddress1);
        NullUtils.updateIfChanged(userAddress::setUsdrAdress2, updatedEmployeeDto.getEmpAddress2(), userAddress::getUsdrAdress2);
        // // Update the employee details
        // existingEmployee.setEmpName(updatedEmployeeDto.getEmpName());
        // LocalDateTime empJoinDate = LocalDateTime.parse(updatedEmployeeDto.getEmpJoinDate());
        // existingEmployee.setEmpJoinDate(empJoinDate);
        // existingEmployee.setEmpGraduate(EnumClassHR.emp_graduate.valueOf(updatedEmployeeDto.getEmpGraduate()));
        // existingEmployee.setEmpStatus(EnumClassHR.status.ACTIVE);
        // existingEmployee.setEmpNetSalary(updatedEmployeeDto.getEmpSalary());
        // existingEmployee.setEmpAccountNumber(updatedEmployeeDto.getEmpAccountNumber());
        // existingEmployee.setEmpJobCode(updatedEmployeeDto.getEmpRole());
    
        // // Update associated user details
        // User user = existingEmployee.getUser();
        // user.setUserName(updatedEmployeeDto.getEmpName());
        // user.setUserEmail(updatedEmployeeDto.getEmpEmail());
    
        // // Update associated user phone details
        // UserPhone userPhone = userPhoneRepository.findByUserUserEntityId(user.getUserEntityId());
        // userPhone.setUsphPhoneType("HP");
        // userPhone.setUsphModifiedDate(LocalDateTime.now());
        // userPhoneRepository.save(userPhone);
    
        // // Update associated user address details
        // UserAddress userAddress = userAddressRepository.findByUserUserEntityId(user.getUserEntityId());
        // userAddress.setUsdrAddress1(updatedEmployeeDto.getEmpAddress());
        // userAddress.setUsdrAdress2(updatedEmployeeDto.getEmpAddress2());
        // userAddress.setCity(cityRepository.findByCityName(updatedEmployeeDto.getEmpCity()));
        // userAddressRepository.save(userAddress);
    
        // // Save the updated entities
        // userServiceImpl.save(user);
        // employeesRepository.save(existingEmployee);

        // updatedEmployeeDto.setEmpName(updatedEmployeeDto.getEmpName());
        // updatedEmployeeDto.setEmpEmail(updatedEmployeeDto.getEmpEmail());
        // updatedEmployeeDto.setEmpPhone(updatedEmployeeDto.getEmpPhone());
        // updatedEmployeeDto.setEmpJoinDate(updatedEmployeeDto.getEmpJoinDate());
        // updatedEmployeeDto.setEmpGraduate(updatedEmployeeDto.getEmpGraduate());
        // updatedEmployeeDto.setEmpRole(updatedEmployeeDto.getEmpRole());
        // updatedEmployeeDto.setEmpSalary(updatedEmployeeDto.getEmpSalary());
        // updatedEmployeeDto.setEmpCity(updatedEmployeeDto.getEmpCity());
        // updatedEmployeeDto.setEmpAddress(updatedEmployeeDto.getEmpAddress());
        // updatedEmployeeDto.setEmpAddress2(updatedEmployeeDto.getEmpAddress2());
    
        // Return the updated DTO
        return updatedEmployeeDto;
    }
    

    @Override
    public List<EmployeesDto> getAllEmployeesDto(){
        EmployeesDto employee = new EmployeesDto();
        employee.getEmpName();
        employee.getEmpJoinDate();
        employee.getEmpGraduate();
        employee.getEmpRole();
        employee.getEmpSalary();
        employee.getEmpAccountNumber();

        return List.of(employee);
    }

    @Override
    public Page<Employees> searchEmployees(String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        EnumClassHR.emp_graduate empGraduate = valueEmpGraduate(value);

        if (empGraduate != null) {
            return employeesRepository.findByEmpGraduate(empGraduate, pageable);
        } else {
            return employeesRepository.findByEmpNameContaining(value, pageable);
        }
    }

    private EnumClassHR.emp_graduate valueEmpGraduate(String value) {
        try {
            return EnumClassHR.emp_graduate.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    

    @Override
    public List<Employees> getAllByEmployeeName(String employeeName) {
        return employeesRepository.findAllByEmpName(employeeName);
    }
    
    @Override
    public Employees getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<Employees> getAll() {
        // TODO Auto-generated method stub
        return employeesRepository.findAll();
    }

    @Override
    public Employees save(Employees entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void deleteById(Long emp_entityid) {
        // TODO Auto-generated method stub
         employeesRepository.deleteById(emp_entityid);
    }
    
}
