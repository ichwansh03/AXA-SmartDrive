package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EmployeesNotFoundException;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneIdDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.hr.JobType;
import com.app.smartdrive.api.entities.hr.EnumClassHR.emp_type;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.entities.users.EnumUsers.RoleName;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.HR.JobTypeRepository;
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

    private final JobTypeRepository jobTypeRepository;

    private final EmployeesRepository employeesRepository;

    private final UserUserAccountService userAccountService;
  
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationService authenticationService;


    @Override
    @Transactional
    public EmployeesResponseDto createEmployee(EmployeesRequestDto employeesDto) {
    
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

        JobType jobType = jobTypeRepository.findById(employeesDto.getJobType()).orElseThrow(() ->
                new EntityNotFoundException("JobType with id " + employeesDto.getJobType() + " not found"));

        authenticationService.validateUsername(employeesDto.getEmail());

        authenticationService.validateEmail(employeesDto.getEmail());
        authenticationService.validateUserPhone(employeesDto.getEmpPhone().getUsphPhoneNumber());

        User user = createUserFromDto(employeesDto);

        Employees employee = Employees.builder()
                .empName(employeesDto.getEmpName())
                .empJoinDate(empJoinDate)
                .empStatus(EnumClassHR.status.ACTIVE)
                .empType(emp_type.PERMANENT)
                .empGraduate(employeesDto.getEmpGraduate())
                .empAccountNumber(employeesDto.getEmpAccountNumber())
                .empNetSalary(employeesDto.getEmpSalary())
                .jobType(jobType)
                .empJobCode(employeesDto.getJobType())
                .user(user)
                .empEntityid(user.getUserEntityId())
                .empModifiedDate(LocalDateTime.now())
                .build();

        if(employeesDto.getGrantAccessUser()){
            user.setUserName(employeesDto.getEmail());
            user.setUserPassword(passwordEncoder.encode(employeesDto.getEmpPhone().getUsphPhoneNumber()));
        }

        userRolesService.createUserRole(RoleName.EM,user,employeesDto.getGrantAccessUser());
        createUserPhonefromDto(user, employeesDto.getEmpPhone());
        createUserAccountFromDto(user, employeesDto.getEmpAccountNumber());
        createUserAddressFromDto(user, employeesDto.getEmpAddress());

        Employees saveEmp = employeesRepository.save(employee);

        return TransactionMapper.mapEntityToDto(saveEmp,EmployeesResponseDto.class);
    }

    private User createUserFromDto(EmployeesRequestDto employeesDto) {
        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setUserEmail(employeesDto.getEmail());
        profileRequestDto.setUserFullName(employeesDto.getEmpName());
        profileRequestDto.setUserBirthPlace(employeesDto.getUserBirthPlace());
        profileRequestDto.setUserNationalId("idn" + employeesDto.getEmail());
        profileRequestDto.setUserNpwp("npwp" + employeesDto.getEmail());
        User user = userService.createUser(profileRequestDto);
    
        return user;
    }

    private void createUserAccountFromDto(User user, String accountNo){
        UserUserAccountDto userAccountDto = new UserUserAccountDto();    
        userAccountDto.setUsac_accountno(accountNo);
        userAccountDto.setEnumPaymentType(EnumPaymentType.BANK);
        List<UserUserAccountDto> listUserAccount = new ArrayList<>();
        listUserAccount.add(userAccountDto);
        userAccountService.createUserAccounts(listUserAccount, user, 1L);
    }

    private void createUserPhonefromDto(User user, UserPhoneRequestDto userPhoneDto) {
        UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto();
        userPhoneIdDto.setUsphPhoneNumber(userPhoneDto.getUsphPhoneNumber());
    
        UserPhoneDto newUserPhoneDto = new UserPhoneDto();
        newUserPhoneDto.setUserPhoneId(userPhoneIdDto);
        newUserPhoneDto.setUsphPhoneType("HP");
    
        List<UserPhoneDto> listPhone = new ArrayList<>();
        listPhone.add(newUserPhoneDto);
        userPhoneService.createUserPhone(user, listPhone);
    }

    private void createUserAddressFromDto(User user, UserAddressRequestDto userAddressDto) {
        UserAddressDto newUserAddressDto = new UserAddressDto();
        newUserAddressDto.setUsdrAddress1(userAddressDto.getUsdrAddress1());
        newUserAddressDto.setUsdrAddress2(userAddressDto.getUsdrAddress2());
    
        List<UserAddressDto> listAddress = new ArrayList<>();
        listAddress.add(newUserAddressDto);
    
        userAddressService.createUserAddress(user, listAddress, userAddressDto.getCityId());  
    }

    @Override
    @Transactional
    public EmployeesResponseDto updateEmployee(Long employeeId, EmployeesRequestDto employeesDto) {
    
        Employees existingEmployee = getById(employeeId);

        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());
        JobType jobType = jobTypeRepository.findById(employeesDto.getJobType()).get();

        authenticationService.validateUsername(employeesDto.getEmail());
        authenticationService.validateEmail(employeesDto.getEmail());
        authenticationService.validateUserPhone(employeesDto.getEmpPhone().getUsphPhoneNumber());

        existingEmployee.setEmpName(employeesDto.getEmpName());
        existingEmployee.setEmpJoinDate(empJoinDate);
        existingEmployee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
        existingEmployee.setEmpGraduate(employeesDto.getEmpGraduate());
        existingEmployee.setEmpNetSalary(employeesDto.getEmpSalary());
        existingEmployee.setJobType(jobType);
        existingEmployee.setEmpModifiedDate(LocalDateTime.now());

        User user = existingEmployee.getUser();

        updateUserFromDto(user, employeesDto);



        if(employeesDto.getGrantAccessUser()){
            user.setUserName(employeesDto.getEmail());
            user.setUserPassword(passwordEncoder.encode(employeesDto.getEmpPhone().getUsphPhoneNumber()));
            userRolesService.updateUserRoleStatus(existingEmployee.getEmpEntityid(),RoleName.EM,"ACTIVE");
        }else{
            user.setUserName(null);
            user.setUserPassword(null);
            userRolesService.updateUserRoleStatus(existingEmployee.getEmpEntityid(),RoleName.EM,"INACTIVE");
        }

        updateAddressEmployees(user, employeesDto.getEmpAddress());
        updateEmployeePhone(user, employeesDto.getEmpPhone());

        Employees saveEmp = employeesRepository.save(existingEmployee);

        return TransactionMapper.mapEntityToDto(saveEmp,EmployeesResponseDto.class);
    }

    private void updateUserFromDto(User user, EmployeesRequestDto employeesDto) {
        user.setUserEmail(employeesDto.getEmail());
        user.setUserFullName(employeesDto.getEmpName());
        user.setUserBirthPlace(employeesDto.getUserBirthPlace());
    }

    private void updateEmployeePhone(User user, UserPhoneRequestDto userPhoneDto) {
        String empPhone = user.getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber();
        userPhoneDto.setUsphPhoneNumber(empPhone);
        userPhoneDto.setUsphPhoneType("HP");
    
        userPhoneService.updateUserPhone(user.getUserEntityId(), user.getUserPhone().get(0).getUserPhoneId().getUsphPhoneNumber(), userPhoneDto);
    }

    private void updateAddressEmployees(User user,UserAddressRequestDto userAddressRequestDto){
        UserAddress userAddress = user.getUserAddress().get(0);
        userAddress.setUsdrAddress1(userAddressRequestDto.getUsdrAddress1());
        userAddress.setUsdrAddress2(userAddress.getUsdrAddress2());
        userAddress.setUsdrCityId(userAddress.getUsdrCityId());
        userAddressService.updateUserAddress(user.getUserEntityId(), userAddress.getUsdrId(), userAddressRequestDto);
    }

    @Override
    public Page<EmployeesResponseDto> searchEmployees(String value, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employees> employeesPage = employeesRepository.findByEmpNameOrEmpGraduate(value, pageable);
        List<EmployeesResponseDto> employeesResponseList = employeesPage.getContent().stream()
                .map(emp -> TransactionMapper.mapEntityToDto(emp, EmployeesResponseDto.class))
                .toList();
        return new PageImpl<>(employeesResponseList, pageable, employeesPage.getTotalElements());
    }

    @Override
  public void deleteEmployeesById(Long empEntitiyid) {
    employeesRepository.deleteById(empEntitiyid);
  }

    @Override
    public Employees getById(Long id) {

        Employees existEmployees = this.employeesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee with id : " + id + " is not found")
        );

        return existEmployees;
    }
    @Override
    @Transactional
    public Page<EmployeesResponseDto> getAll(Pageable pageable) {

        Page<Employees> employeesPage = employeesRepository.findAll(pageable);

        List<EmployeesResponseDto> employeesResponseList = employeesPage.getContent().stream()
                .map(emp -> TransactionMapper.mapEntityToDto(emp, EmployeesResponseDto.class))
                .toList();
        return new PageImpl<>(employeesResponseList, pageable, employeesPage.getTotalElements());
    }
}
