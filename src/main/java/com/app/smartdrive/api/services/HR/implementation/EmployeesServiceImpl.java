package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;

import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserCityDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneIdDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.hr.JobType;
import com.app.smartdrive.api.entities.hr.EnumClassHR.emp_type;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.entities.users.UserAdressId;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.entities.users.UserRoles;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.mapper.hr.EmployeesMapper;
import com.app.smartdrive.api.mapper.payment.BanksMapper;
import com.app.smartdrive.api.mapper.user.UserMapper;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.HR.JobTypeRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserAddressRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.payment.UserAccountsService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserAddressService;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.implementation.UserAddressImpl;
import com.app.smartdrive.api.services.users.implementation.UserPhoneImpl;
import com.app.smartdrive.api.services.users.implementation.UserRolesImpl;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;
import com.app.smartdrive.api.utils.NullUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {
    
    private final BusinessEntityService businessEntityService;

    private final UserAddressService userAddressService;

    private final UserPhoneService userPhoneService;

    private final UserRolesService userRolesService;

    private final EmployeesRepository employeesRepository;
  
    private final UserService userService;




    @Override
    @Transactional
    public EmployeesRequestDto createEmployee(EmployeesRequestDto employeesDto) {
    
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

        Employees employee = new Employees();
        
        employee.setEmpName(employeesDto.getEmpName());
        employee.setEmpJoinDate(empJoinDate);
        employee.setEmpStatus(EnumClassHR.status.ACTIVE);
        employee.setEmpType(emp_type.PERMANENT);
        employee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
        employee.setEmpGraduate(employeesDto.getEmpGraduate());
        employee.setEmpNetSalary(employeesDto.getEmpSalary());
        employee.setEmpJobCode(employeesDto.getEmpJobRole().getJobCode());
        employee.setEmpModifiedDate(LocalDateTime.now());

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setUserEmail(employeesDto.getEmail());
        profileRequestDto.setUserFullName(employeesDto.getEmpName());
        
        User user = userService.createUser(profileRequestDto);
        
        if(employeesDto.getGrantAccessUser()==true){
            user.setUserEmail(employeesDto.getEmail());
            user.setUserName(employeesDto.getEmail());
            user.setUserPassword(employeesDto.getEmpPhone().getUsphPhoneNumber());
            user.setUserFullName(employeesDto.getEmpName());
            user.setUserNationalId("idn"+user.getUserEntityId());
            user.setUserNPWP("npwp"+user.getUserEntityId());
        
            
            userRolesService.createUserRole(RoleName.EM, user);
            

            UserPhoneDto userPhoneDto = new UserPhoneDto();
            UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto();
            userPhoneIdDto.setUsphPhoneNumber(employeesDto.getEmpPhone().getUsphPhoneNumber());
            userPhoneDto.setUserPhoneId(userPhoneIdDto);
            List<UserPhoneDto> listPhone = new ArrayList<>();
            listPhone.add(userPhoneDto);
            
            userPhoneService.createUserPhone(user, listPhone);
        
            UserAddressDto userAddressDto = new UserAddressDto();
            userAddressDto.setUsdrAddress1(employeesDto.getEmpAddress().getUsdrAddress1());
            userAddressDto.setUsdrAddress2(employeesDto.getEmpAddress().getUsdrAddress2());
            List<UserAddressDto> listAddress = new ArrayList<>();
            listAddress.add(userAddressDto);
            userAddressService.createUserAddress(user, listAddress, employeesDto.getEmpAddress().getCityId());
        }
        
        employee.setEmpEntityid(user.getUserEntityId());
        employee.setUser(user);
    
        

        employeesRepository.save(employee);
        

        return employeesDto;
    }

    @Override
    @Transactional
    public EmployeesRequestDto editEmployee(Long employeeId, EmployeesRequestDto employeesDto) {
    
    Employees existingEmployee = employeesRepository.findById(employeeId)
            .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

    LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

    User user = existingEmployee.getUser();
    user.setUserEmail(employeesDto.getEmail());
    user.setUserName(employeesDto.getEmail());
    user.setUserPassword(employeesDto.getEmpPhone().getUsphPhoneNumber());
    user.setUserFullName(employeesDto.getEmpName());

    String empPhone = user.getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber();
    UserPhoneRequestDto userPhoneDto = employeesDto.getEmpPhone();
    userPhoneDto.setUsphPhoneNumber(employeesDto.getEmpPhone().getUsphPhoneNumber());
    userPhoneDto.setUsphPhoneType("HP");
    userPhoneService.updateUserPhone(employeeId, empPhone, userPhoneDto);

    UserAddress userAddress = user.getUserAddress().get(0);
    UserAddressRequestDto userAddressRequestDto = employeesDto.getEmpAddress();
    userAddressRequestDto.setUsdrAddress1(employeesDto.getEmpAddress().getUsdrAddress1());
    userAddressRequestDto.setUsdrAddress2(employeesDto.getEmpAddress().getUsdrAddress2());
    userAddressRequestDto.setCityId(employeesDto.getEmpAddress().getCityId());
    userAddressService.updateUserAddress(employeeId, userAddress.getUsdrId(), userAddressRequestDto);

    
    existingEmployee.setEmpName(employeesDto.getEmpName());
    existingEmployee.setEmpJoinDate(empJoinDate);
    existingEmployee.setEmpStatus(EnumClassHR.status.ACTIVE);
    existingEmployee.setEmpType(emp_type.PERMANENT);
    existingEmployee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
    existingEmployee.setEmpGraduate(employeesDto.getEmpGraduate());
    existingEmployee.setEmpNetSalary(employeesDto.getEmpSalary());
    existingEmployee.setEmpJobCode(employeesDto.getEmpJobRole().getJobCode());
    existingEmployee.setEmpModifiedDate(LocalDateTime.now());

    
    employeesRepository.save(existingEmployee);

    
    return employeesDto;
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
    public Employees save(Employees entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<EmployeesRequestDto> getAllEmployeesDto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEmployeesDto'");
    }

    @Override
    public List<Employees> getAll() {
        List<Employees> empList = employeesRepository.findAll();
        List<EmployeesDto> empDto = new ArrayList<>();
        for (Employees emp : empList) {
            EmployeesDto dto = EmployeesMapper.convertEntityToDto(emp);
            empDto.add(dto);
        }
        return empList;
    }
    

    
}
