package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.EnumUsers;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.HR.JobTypeRepository;
import com.app.smartdrive.api.repositories.master.AreaWorkGroupRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.repositories.users.UserAddressRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {
    
    
    private final EmployeesRepository employeesRepository;

    private final UserRepository userRepository;
    
    private final UserPhoneRepository userPhoneRepository;

    private final UserAddressRepository userAddressRepository;

    private final CityRepository cityRepository;

    private final BusinessEntityRepository businessEntityRepository;



    
    
    @Override
    @Transactional
    public EmployeesDto addEmployee(EmployeesDto employeesDto) {
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());
    
        User user = new User();
        BusinessEntity userBusinessEntity = new BusinessEntity();
        userBusinessEntity.setEntityModifiedDate(LocalDateTime.now());
        
        
        user.setUserBusinessEntity(userBusinessEntity);
        user.setUserName(employeesDto.getEmpName());
        user.setUserEmail(employeesDto.getEmpEmail());
        user.setUserModifiedDate(LocalDateTime.now());
        user.setUserNationalId("idnn" + user.getUserEntityId());
    
        // UserRoles userRoles = new UserRoles();
        // UserRolesId userRolesId = new UserRolesId();
        // userRoles.setUserRolesId(userRolesId);
        // userRolesId.setUsroRoleName(EnumUsers.roleName.EM);
        // userRolesId.setUsroEntityId(user.getUserEntityId());
        // userRoles.setUsroModifiedDate(LocalDateTime.now());
    
        UserPhone userPhone = new UserPhone();
        UserPhoneId userPhoneId = new UserPhoneId();
        userPhone.setUserPhoneId(userPhoneId);
        userPhoneId.setUsphEntityId(user.getUserEntityId());
        userPhoneId.setUsphPhoneNumber(employeesDto.getEmpPhone());
        userPhone.setUsphPhoneType("HP");
        userPhone.setUsphModifiedDate(LocalDateTime.now());
        userPhone.setUser(user);
    
        Cities empCityEntity = cityRepository.findByCityName(employeesDto.getEmpCity());
    
        UserAddress userAddress = new UserAddress();
        UserAdressId userAdressId = new UserAdressId();
        userAddress.setUserAdressId(userAdressId);
        userAdressId.setUsdrEntityId(user.getUserEntityId());
        userAddress.setUsdrAddress1(employeesDto.getEmpAddress());
        userAddress.setUsdrAdress2(employeesDto.getEmpAddress2());
        userAddress.setUser(user);
        userAddress.setCity(empCityEntity);
    
        Employees employee = new Employees();
        employee.setEmpEntityid(user.getUserEntityId());
        employee.setEmpName(employeesDto.getEmpName());
        employee.setEmpJoinDate(empJoinDate);
        employee.setEmpGraduate(EnumClassHR.emp_graduate.valueOf(employeesDto.getEmpGraduate()));
        employee.setEmpStatus(EnumClassHR.status.ACTIVE);
        employee.setEmpNetSalary(employeesDto.getEmpSalary());
        employee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
        employee.setUser(user);
        employee.setEmpJobCode(employeesDto.getEmpRole());
    
        userRepository.save(user);
        userPhoneRepository.save(userPhone);
        userAddressRepository.save(userAddress);

        userBusinessEntity.setUser(user);
        businessEntityRepository.save(userBusinessEntity);
    
         employeesRepository.save(employee);

        EmployeesDto addedEmployeeDto = new EmployeesDto();
        addedEmployeeDto.setEmpName(employee.getEmpName());
        addedEmployeeDto.setEmpJoinDate(employee.getEmpJoinDate().toString());
        addedEmployeeDto.setEmpGraduate(employee.getEmpGraduate().toString());
        addedEmployeeDto.setEmpSalary(employee.getEmpNetSalary());
        addedEmployeeDto.setEmpAccountNumber(employee.getEmpAccountNumber());
        addedEmployeeDto.setEmpRole(employee.getEmpJobCode());
        addedEmployeeDto.setEmpCity(employeesDto.getEmpCity());
        addedEmployeeDto.setEmpAddress(employeesDto.getEmpAddress());
        addedEmployeeDto.setEmpAddress2(employeesDto.getEmpAddress2());

        return addedEmployeeDto;
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
    public List<Employees> getAllByEmployeeName(String employeeName) {
        return employeesRepository.findAllByEmployeeName(employeeName);
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
    public void deleteById(Long empEntityid) {
        // TODO Auto-generated method stub
         employeesRepository.deleteById(empEntityid);
    }
    
}
