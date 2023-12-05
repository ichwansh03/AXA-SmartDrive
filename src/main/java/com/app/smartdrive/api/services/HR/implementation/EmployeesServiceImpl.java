package com.app.smartdrive.api.services.HR.implementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.HR.request.CreateEmployeesDto;

import com.app.smartdrive.api.dto.HR.response.EmployeesDto;
import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.dto.user.response.UserCityDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneIdDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.entities.hr.JobType;
import com.app.smartdrive.api.entities.hr.EnumClassHR.emp_type;
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
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.mapper.hr.EmployeesMapper;
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

    private final UserAccountsService userAccountsService;

    private final UserPhoneRepository userPhoneRepository;

    private final UserRolesService userRolesService;

    private final EmployeesRepository employeesRepository;

    private final RolesRepository rolesRepository;

    private final UserRoleRepository userRoleRepository;
    
    private final UserService userService;

    private final JobTypeRepository jobTypeRepository;


    @Override
    @Transactional
    public CreateEmployeesDto addEmployee(CreateEmployeesDto employeesDto){
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());



    


        
    
        // User user = new User();
        // BusinessEntity businessEntity = new BusinessEntity();
        // businessEntity.setEntityModifiedDate(LocalDateTime.now());


        // // Save the business entity and get the ID
        // Long businessEntityId = businessEntityService.save(businessEntity); 

        // List<UserPhone> userPhones = user.getUserPhone();
        // StringBuilder passwordBuilder = new StringBuilder();

        // for (UserPhone up : userPhones) {
        //     passwordBuilder.append(up.getUserPhoneId().getUsphPhoneNumber());
        // }
        

        // // if(employeesDto.getGrantUserAccess()){
        // user.setUserBusinessEntity(businessEntity);
        // user.setUserEntityId(businessEntityId);
        // user.setUserName(employeesDto.getUser().getUserEmail());
        // user.setUserPassword(passwordBuilder.toString());
        // user.setUserFullName(employeesDto.getEmpName());
        // user.setUserEmail(employeesDto.getUser().getUserEmail());
        // user.setUserModifiedDate(LocalDateTime.now());
        // user.setUserNationalId("ind"+businessEntityId);
        // user.setUserNPWP("npwp"+businessEntityId);
        // // Save the business entity and get the ID
        // Long businessEntityId = businessEntityService.save(businessEntity); 

        // // if(employeesDto.getGrantUserAccess()){
        // user.setUserBusinessEntity(businessEntity);
        // user.setUserEntityId(businessEntityId);
        // user.setUserName(employeesDto.getEmail());
        // user.setUserPassword(employeesDto.getUserPhone().getUserPhoneId().getUsphPhoneNumber());
        // user.setUserFullName(employeesDto.getEmpName());
        // user.setUserEmail(employeesDto.getEmail());
        // user.setUserModifiedDate(LocalDateTime.now());
        // user.setUserNationalId("ind"+businessEntityId);
        // user.setUserNPWP("npwp"+businessEntityId);

    
        
        // UserRolesId userRolesId = new UserRolesId(businessEntityId, RoleName.EM);
        // Roles roles = rolesRepository.findById(RoleName.EM).get();
        
        // UserRoles userRoles = new UserRoles();
        // userRoles.setUserRolesId(userRolesId);
        // userRoles.setRoles(roles);
        // userRoles.setUsroStatus("ACTIVE");
        // userRoles.setUsroModifiedDate(LocalDateTime.now());
        // userRoles.setUser(user);
        

        // List<UserRoles> listRole = List.of(userRoles);
        
        // List<UserRoles> listRole = List.of(userRoles);


        // UserPhone userPhone = new UserPhone();
        // UserPhoneId userPhoneId = new UserPhoneId(businessEntityId, employeesDto.get);
        // userPhone.setUserPhoneId(userPhoneId);
        // userPhone.setUsphPhoneType("HP");
        // userPhone.setUsphModifiedDate(LocalDateTime.now());
        // userPhone.setUser(user);
        // List<UserPhone> listUserPhones = List.of(userPhone);
    
        // Cities empCityEntity = cityRepository.findByCityName(employeesDto.getUserAddress().getCity().getCityName());
    
        // UserAddress userAddress = new UserAddress();
        // userAddress.setUsdrEntityId(businessEntityId);
        // userAddress.setUsdrAddress1(employeesDto.getUserAddress().getUsdrAddress1());
        // userAddress.setUsdrAddress2(employeesDto.getUserAddress().getUsdrAddress2());
        // userAddress.setCity(empCityEntity);
        // userAddress.setUser(user);
        
        // List<UserAddress> listuAddresses = List.of(userAddress);
        
        

        // JobType jobType = jobTypeRepository.findById(employeesDto.getEmpJobType().getJobCode()).get();
        // Employees employee = new Employees();
        // employee.setEmpEntityid(user.getUserEntityId());
        // employee.setEmpName(employeesDto.getEmpName());
        // employee.setEmpJoinDate(empJoinDate);
        // employee.setEmpGraduate(EnumClassHR.emp_graduate.valueOf(employeesDto.getEmpGraduate()));
        // employee.setEmpStatus(EnumClassHR.status.ACTIVE);
        // employee.setEmpNetSalary(employeesDto.getEmpSalary());
        // employee.setEmpAccountNumber(employeesDto.getUser().getUserAccounts().get(0).getUsac_accountno());
        // employee.setUser(user);
        // employee.setEmpJobCode(employeesDto.getEmpJobType().getJobCode());
        // employee.setJobType(jobType);

        // user.setUserName(employeesDto.getUser().getUserEmail());
        // user.setUserPassword(employeesDto.getUser().getUserPhone().get();

        // user.setUserRoles(listRole);
        // user.setUserPhone(listUserPhones);
        // user.setUserAddress(listuAddresses);


        // employeesRepository.save(employee);

        
    
        return employeesDto;
    }

    @Override
    @Transactional
    public CreateEmployeesDto createEmployee(CreateEmployeesDto employeesDto) {
    
        LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        
        User user = userService.createUser(profileRequestDto);
        if(employeesDto.getGrantAccessUser()==true){
        user.setUserEmail(employeesDto.getEmail());
        user.setUserName(employeesDto.getEmail());
        user.setUserPassword(employeesDto.getEmpPhone());
        user.setUserFullName(employeesDto.getEmpName());
        user.setUserNationalId("idn"+user.getUserFullName());
        user.setUserNPWP("npwp"+user.getUserFullName());
    
        userRolesService.createUserRole(RoleName.EM, user);

        UserPhoneDto userPhoneDto = new UserPhoneDto();
        UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto();
        userPhoneIdDto.setUsphPhoneNumber(employeesDto.getEmpPhone());
        userPhoneDto.setUserPhoneId(userPhoneIdDto);
        List<UserPhoneDto> listPhone = new ArrayList<>();
        listPhone.add(userPhoneDto);
        
        userPhoneService.createUserPhone(user, listPhone);
    
        UserAddressDto userAddressDto = new UserAddressDto();
        userAddressDto.setUsdrAddress1(employeesDto.getEmpAddress());
        userAddressDto.setUsdrAddress2(employeesDto.getEmpAddress2());
        List<UserAddressDto> listAddress = new ArrayList<>();
        listAddress.add(userAddressDto);
        userAddressService.createUserAddress(user, listAddress, employeesDto.getEmpCity());
        }
        
    
        Employees employee = new Employees();
        employee.setUser(user);
        employee.setEmpEntityid(user.getUserEntityId());
        employee.setEmpName(employeesDto.getEmpName());
        employee.setEmpJoinDate(empJoinDate);
        employee.setEmpStatus(EnumClassHR.status.ACTIVE);
        employee.setEmpType(emp_type.PERMANENT);
        employee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
        employee.setEmpGraduate(employeesDto.getEmpGraduate());
        employee.setEmpNetSalary(employeesDto.getEmpSalary());
        employee.setEmpJobCode(employeesDto.getEmpJobRole());
        employee.setEmpModifiedDate(LocalDateTime.now());

        employeesRepository.save(employee);
        

        return employeesDto;
    }

    @Override
    @Transactional
    public CreateEmployeesDto editEmployee(Long employeeId, CreateEmployeesDto employeesDto) {
    
    Employees existingEmployee = employeesRepository.findById(employeeId)
            .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

    LocalDateTime empJoinDate = LocalDateTime.parse(employeesDto.getEmpJoinDate());

    User user = existingEmployee.getUser();
    user.setUserEmail(employeesDto.getEmail());
    user.setUserName(employeesDto.getEmail());
    user.setUserPassword(employeesDto.getEmpPhone());
    user.setUserFullName(employeesDto.getEmpName());

    String empPhone = employeesDto.getEmpPhone();
    if (empPhone != null && !empPhone.isEmpty()) {
        userPhoneService.updateUserPhone(user.getUserEntityId(), empPhone, null);
    }
    // UserPhoneIdDto userPhoneIdDto = new UserPhoneIdDto();
    // userPhoneIdDto.setUsphPhoneNumber(employeesDto.getEmpPhone());
    // userPhoneDto.setUserPhoneId(userPhoneIdDto);
    // List<UserPhoneDto> listPhone = new ArrayList<>();
    // listPhone.add(userPhoneDto);

    List<UserAddressDto> listAddress = new ArrayList<>();
    UserAddressDto userAddressDto = new UserAddressDto();
    userAddressDto.setUsdrAddress1(employeesDto.getEmpAddress());
    userAddressDto.setUsdrAddress2(employeesDto.getEmpAddress2());
    listAddress.add(userAddressDto);
    userAddressService.createUserAddress(user, listAddress, employeesDto.getEmpCity());

    
    existingEmployee.setEmpName(employeesDto.getEmpName());
    existingEmployee.setEmpJoinDate(empJoinDate);
    existingEmployee.setEmpStatus(EnumClassHR.status.ACTIVE);
    existingEmployee.setEmpType(emp_type.PERMANENT);
    existingEmployee.setEmpAccountNumber(employeesDto.getEmpAccountNumber());
    existingEmployee.setEmpGraduate(employeesDto.getEmpGraduate());
    existingEmployee.setEmpNetSalary(employeesDto.getEmpSalary());
    existingEmployee.setEmpJobCode(employeesDto.getEmpJobRole());
    existingEmployee.setEmpModifiedDate(LocalDateTime.now());

    
    employeesRepository.save(existingEmployee);

    
    return employeesDto;
}
    // @Override
    // public void createUser(){
    //     User user = new User();
    //     BusinessEntity businessEntity = new BusinessEntity();
    //     businessEntity.setEntityModifiedDate(LocalDateTime.now());
    //     // Save the business entity and get the ID
    //     Long businessEntityId = businessEntityService.save(businessEntity); 

        
    //     // if(employeesDto.getGrantUserAccess()){
    //     user.setUserBusinessEntity(businessEntity);
    //     user.setUserEntityId(businessEntityId);
    //     user.setUserName("eada");
    //     user.setUserPassword("adad");
    //     user.setUserFullName("dasas adosd");
    //     user.setUserEmail("adsa@gmail.com");
    //     user.setUserModifiedDate(LocalDateTime.now());
    //     user.setUserNationalId("iyynd");
    //     user.setUserNPWP("npwp");
        
    //     UserRolesId userRolesId = new UserRolesId(businessEntityId, RoleName.EM);
    //     Roles roles = rolesRepository.findById(RoleName.EM).get();
        
    //     UserRoles userRoles = new UserRoles();
    //     userRoles.setUserRolesId(userRolesId);
    //     userRoles.setRoles(roles);
    //     userRoles.setUsroStatus("ACTIVE");
    //     userRoles.setUsroModifiedDate(LocalDateTime.now());
    //     userRoles.setUser(user);
    //     List<UserRoles> listRole = List.of(userRoles);
    //     user.setUserRoles(listRole);

        
    // }

    

    // @Override
    // @Transactional
    // public CreateEmployeesDto updateEmployee(Long employeeId, CreateEmployeesDto updatedEmployeeDto) {
    //     // Retrieve the existing employee entity from the database
    //     Employees existingEmployee = employeesRepository.findById(employeeId)
    //             .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

    //     User user = existingEmployee.getUser(); 

    //     UserAddress userAddress = userAddressRepository.findByUserUserEntityId(user.getUserEntityId());
    //     userAddress.setCity(cityRepository.findByCityName(updatedEmployeeDto.getUserAddress().getCity().getCityName()));

    //     LocalDateTime empJoinDate = LocalDateTime.parse(updatedEmployeeDto.getEmpJoinDate());
    //     EnumClassHR.emp_graduate empGraduate = EnumClassHR.emp_graduate.valueOf(updatedEmployeeDto.getEmpGraduate());
    //     NullUtils.updateIfChanged(existingEmployee::setEmpName, updatedEmployeeDto.getEmpName(), existingEmployee::getEmpName);
    //     NullUtils.updateIfChanged(existingEmployee::setEmpJoinDate,empJoinDate,existingEmployee::getEmpJoinDate);
    //     NullUtils.updateIfChanged(existingEmployee::setEmpGraduate, empGraduate, existingEmployee::getEmpGraduate);
    //     NullUtils.updateIfChanged(existingEmployee::setEmpNetSalary, updatedEmployeeDto.getEmpSalary(), existingEmployee::getEmpNetSalary);
    //     NullUtils.updateIfChanged(existingEmployee::setEmpAccountNumber, updatedEmployeeDto.getEmpAccountNumber(), existingEmployee::getEmpAccountNumber);
    //     NullUtils.updateIfChanged(user::setUserEmail, updatedEmployeeDto.getEmail(), user::getUserEmail);
    //     NullUtils.updateIfChanged(userAddress::setUsdrAddress1, updatedEmployeeDto.getUserAddress().getUsdrAddress1(), userAddress::getUsdrAddress1);
    //     NullUtils.updateIfChanged(userAddress::setUsdrAddress2, updatedEmployeeDto.getUserAddress().getUsdrAddress2(), userAddress::getUsdrAddress2);
    //     // // Update the employee details
    //     // existingEmployee.setEmpName(updatedEmployeeDto.getEmpName());
    //     // LocalDateTime empJoinDate = LocalDateTime.parse(updatedEmployeeDto.getEmpJoinDate());
    //     // existingEmployee.setEmpJoinDate(empJoinDate);
    //     // existingEmployee.setEmpGraduate(EnumClassHR.emp_graduate.valueOf(updatedEmployeeDto.getEmpGraduate()));
    //     // existingEmployee.setEmpStatus(EnumClassHR.status.ACTIVE);
    //     // existingEmployee.setEmpNetSalary(updatedEmployeeDto.getEmpSalary());
    //     // existingEmployee.setEmpAccountNumber(updatedEmployeeDto.getEmpAccountNumber());
    //     // existingEmployee.setEmpJobCode(updatedEmployeeDto.getEmpRole());

    //     // // Update associated user details
    //     // User user = existingEmployee.getUser();
    //     // user.setUserName(updatedEmployeeDto.getEmpName());
    //     // user.setUserEmail(updatedEmployeeDto.getEmpEmail());
    
    //     // // Update associated user phone details
    //     // UserPhone userPhone = userPhoneRepository.findByUserUserEntityId(user.getUserEntityId());
    //     // userPhone.setUsphPhoneType("HP");
    //     // userPhone.setUsphModifiedDate(LocalDateTime.now());
    //     // userPhoneRepository.save(userPhone);
    
    //     // // Update associated user address details
    //     // UserAddress userAddress = userAddressRepository.findByUserUserEntityId(user.getUserEntityId());
    //     // userAddress.setUsdrAddress1(updatedEmployeeDto.getEmpAddress());
    //     // userAddress.setUsdrAdress2(updatedEmployeeDto.getEmpAddress2());
    //     // userAddress.setCity(cityRepository.findByCityName(updatedEmployeeDto.getEmpCity()));
    //     // userAddressRepository.save(userAddress);
    
    //     // // Save the updated entities
    //     // userServiceImpl.save(user);
    //     // employeesRepository.save(existingEmployee);

    //     // updatedEmployeeDto.setEmpName(updatedEmployeeDto.getEmpName());
    //     // updatedEmployeeDto.setEmpEmail(updatedEmployeeDto.getEmpEmail());
    //     // updatedEmployeeDto.setEmpPhone(updatedEmployeeDto.getEmpPhone());
    //     // updatedEmployeeDto.setEmpJoinDate(updatedEmployeeDto.getEmpJoinDate());
    //     // updatedEmployeeDto.setEmpGraduate(updatedEmployeeDto.getEmpGraduate());
    //     // updatedEmployeeDto.setEmpRole(updatedEmployeeDto.getEmpRole());
    //     // updatedEmployeeDto.setEmpSalary(updatedEmployeeDto.getEmpSalary());
    //     // updatedEmployeeDto.setEmpCity(updatedEmployeeDto.getEmpCity());
    //     // updatedEmployeeDto.setEmpAddress(updatedEmployeeDto.getEmpAddress());
    //     // updatedEmployeeDto.setEmpAddress2(updatedEmployeeDto.getEmpAddress2());
    
    //     // Return the updated DTO
    //     return updatedEmployeeDto;
    // }
    

    @Override
public List<EmployeesDto> getAllDto() {
    List<Employees> employees = employeesRepository.findAll();
    List<EmployeesDto> employeesDtos = new ArrayList<>();
    for (Employees em : employees) {
        employeesDtos.add(EmployeesMapper.convertEntityToDto(em));  
    }
    return employeesDtos;
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

    @Override
    public List<CreateEmployeesDto> getAllEmployeesDto() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEmployeesDto'");
    }

    @Override
    public void createUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
    
}
