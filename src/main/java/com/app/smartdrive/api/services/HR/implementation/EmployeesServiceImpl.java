package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EmployeesNotFoundException;
import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneIdDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.hr.EnumClassHR.emp_type;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.users.UserAddressService;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeesServiceImpl implements EmployeesService {
    

    private final UserAddressService userAddressService;

    private final UserPhoneService userPhoneService;

    private final UserRolesService userRolesService;

    private final EmployeesRepository employeesRepository;

    private final UserUserAccountService userAccountService;
  
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public EmployeesRequestDto createEmployee(EmployeesRequestDto employeesDto) {
    
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

        Employees employee = new Employees();
        
        employee.setEmpName(employeesDto.getEmpName());
        employee.setEmpJoinDate(empJoinDate);
        employee.setEmpStatus(EnumClassHR.status.INACTIVE);
        employee.setEmpType(emp_type.PERMANENT);
        employee.setEmpGraduate(employeesDto.getEmpGraduate());
        employee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
        employee.setEmpNetSalary(employeesDto.getEmpSalary());
        employee.setEmpJobCode(employeesDto.getJobType().getJobCode());
        employee.setEmpModifiedDate(LocalDateTime.now());

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setUserEmail(employeesDto.getEmail());
        profileRequestDto.setUserFullName(employeesDto.getEmpName());
        
        User user = userService.createUser(profileRequestDto);
        
            user.setUserEmail(employeesDto.getEmail());
            if(employeesDto.getGrantAccessUser()==true){
            user.setUserName(employeesDto.getEmail());
            user.setUserPassword(passwordEncoder.encode(employeesDto.getEmpPhone().getUsphPhoneNumber()));
            employee.setEmpStatus(EnumClassHR.status.ACTIVE);
            }
            user.setUserFullName(employeesDto.getEmpName());
            user.setUserNationalId("idn"+user.getUserEntityId());
            user.setUserNPWP("npwp"+user.getUserEntityId());
        
            userRolesService.createUserRole(RoleName.EM, user);

            UserPhoneDto userPhoneDto = new UserPhoneDto();
            UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto();
            userPhoneIdDto.setUsphPhoneNumber(employeesDto.getEmpPhone().getUsphPhoneNumber());
            userPhoneDto.setUserPhoneId(userPhoneIdDto);
            userPhoneDto.setUsphPhoneType("HP");
            List<UserPhoneDto> listPhone = new ArrayList<>();
            listPhone.add(userPhoneDto);
            userPhoneService.createUserPhone(user, listPhone);
        
            UserAddressDto userAddressDto = new UserAddressDto();
            userAddressDto.setUsdrAddress1(employeesDto.getEmpAddress().getUsdrAddress1());
            userAddressDto.setUsdrAddress2(employeesDto.getEmpAddress().getUsdrAddress2());
            List<UserAddressDto> listAddress = new ArrayList<>();
            listAddress.add(userAddressDto);
            userAddressService.createUserAddress(user, listAddress, employeesDto.getEmpAddress().getCityId());     

            UserUserAccountDto userAccountDto = new UserUserAccountDto();
            userAccountDto.setUsac_accountno(employeesDto.getEmpAccountNumber());
            userAccountDto.setEnumPaymentType(EnumPaymentType.BANK);
            List<UserUserAccountDto> listUserAccount = new ArrayList<>();
            listUserAccount.add(userAccountDto);
            userAccountService.createUserAccounts(listUserAccount, user, 1L);
        
        
        employee.setEmpEntityid(user.getUserEntityId());
        employee.setUser(user);    

        employeesRepository.save(employee);

        return employeesDto;
    }

    @Override
    @Transactional
    public EmployeesRequestDto editEmployee(Long employeeId, EmployeesRequestDto employeesDto) {
    
    Employees existingEmployee = employeesRepository.findById(employeeId)
            .orElseThrow(() -> new EmployeesNotFoundException("Employee not found with id: " + employeeId));

    LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

    User user = existingEmployee.getUser();
    user.setUserEmail(employeesDto.getEmail());
    user.setUserName(employeesDto.getEmail());
    user.setUserPassword(employeesDto.getEmpPhone().getUsphPhoneNumber());
    user.setUserFullName(employeesDto.getEmpName());

    if(employeesDto.getGrantAccessUser()==true){
            user.setUserName(employeesDto.getEmail());
            user.setUserPassword(passwordEncoder.encode(employeesDto.getEmpPhone().getUsphPhoneNumber()));
            existingEmployee.setEmpStatus(EnumClassHR.status.ACTIVE);
            }

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
    existingEmployee.setEmpJobCode(employeesDto.getJobType().getJobCode());
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
  public void deleteEmployeesById(Long empEntitiyid) {
    Optional<Employees> emp = employeesRepository.findById(empEntitiyid);
    if(emp.isPresent()){
      employeesRepository.delete(emp.get());
    }
  }

    

    @Override
    public List<EmployeesRequestDto> getAllEmployeesDto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEmployeesDto'");
    }

    @Override
    public List<EmployeesDto> getAllDto() {
      List<Employees> employees = employeesRepository.findAll();
      List<EmployeesDto> empDto = TransactionMapper.mapEntityListToDtoList(employees, EmployeesDto.class);
      return empDto;
    }

    @Override
    public Employees getById(Long id) {
        return employeesRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("employees not found by id "+id);
        });
    }

    @Override
    public List<Employees> getAll() {
        return employeesRepository.findAll();
    }

    @Override
    public Employees save(Employees entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    

    

    
}
