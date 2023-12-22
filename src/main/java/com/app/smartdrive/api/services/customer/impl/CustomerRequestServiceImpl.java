package com.app.smartdrive.api.services.customer.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.UserPhoneExistException;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.customer.*;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.*;
import com.app.smartdrive.api.entities.users.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.users.RolesRepository;
import com.app.smartdrive.api.repositories.users.UserPhoneRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.repositories.users.UserRoleRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.customer.*;
import com.app.smartdrive.api.services.master.*;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerRequestServiceImpl implements CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;

    private final EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;

    private final BusinessEntityService businessEntityService;

    private final UserService userService;

    private final CustomerInscAssetsService customerInscAssetsService;

    private final CustomerInscDocService customerInscDocService;

    private final CustomerInscExtendService customerInscExtendService;

    private final CustomerClaimService customerClaimService;

    private final CarsService carsService;

    private final IntyService intyService;

    private final CityService cityService;

    private final ArwgService arwgService;

    private final EmployeesService employeesService;

    private final PasswordEncoder passwordEncoder;

    private final UserRolesService userRolesService;

    private final UserPhoneService userPhoneService;

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponseDTO> get(){
        List<CustomerRequest> customerRequestList = this.customerRequestRepository.findAll();
        log.info("CustomerRequestServiceImpl::get, get all customer request");
        return TransactionMapper.mapEntityListToDtoList(customerRequestList, CustomerResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponseDTO> getPaging(Pageable pageable){
        Page<CustomerRequest> pageCustomerRequest = this.customerRequestRepository.findAll(pageable);
        Page<CustomerResponseDTO> pageCustomerResponseDTO = pageCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        log.info("CustomerRequestServiceImpl::getPaging, successfully get all customer request with paging");
        return pageCustomerResponseDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponseDTO getCustomerRequestById(Long creqEntityId){
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(creqEntityId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Request with id " + creqEntityId + " is not found")
                );

        log.info("CustomerRequestImpl::getCustomerRequestById in ID {} ", existCustomerRequest.getCreqEntityId());
        return TransactionMapper.mapEntityToDto(existCustomerRequest, CustomerResponseDTO.class);
    }

    @Transactional
    @Override
    public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception {
        // prep
        CiasDTO ciasDTO = customerRequestDTO.getCiasDTO();
        Long[] cuexIds = customerRequestDTO.getCiasDTO().getCuexIds();

        if(this.customerInscAssetsService.isCiasAlreadyExist(ciasDTO.getCiasPoliceNumber())){
            throw new Exception("CustomerRequest with police number " + ciasDTO.getCiasPoliceNumber() + " is already exist");
        }

        BusinessEntity newEntity = this.businessEntityService.createBusinessEntity();
        Long entityId = newEntity.getEntityId();

        User customer = this.userService.getUserById(customerRequestDTO.getCustomerId()).orElseThrow(
                () -> new EntityNotFoundException("User with id " + customerRequestDTO.getCustomerId() + " is not found")
        );

        CarSeries existCarSeries = this.carsService.getById(ciasDTO.getCiasCarsId());

        Cities existCity = this.cityService.getById(ciasDTO.getCiasCityId());

        InsuranceType existInty = this.intyService.getById(ciasDTO.getCiasIntyName());

        EmployeeAreaWorkgroup employeeAreaWorkgroup = this.employeeAreaWorkgroupRepository.findById(new EmployeeAreaWorkgroupId(customerRequestDTO.getAgenId(), customerRequestDTO.getEmployeeId()))
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee Areaworkgroup with id " + customerRequestDTO.getAgenId() + " is not found")
                );


        CustomerRequest newCustomerRequest = this.createCustomerRequest(newEntity, customer, entityId);
        newCustomerRequest.setCreqAgenEntityid(employeeAreaWorkgroup.getEawgId());
        newCustomerRequest.setEmployeeAreaWorkgroup(employeeAreaWorkgroup);

        CustomerInscAssets cias = this.customerInscAssetsService.createCustomerInscAssets(entityId, ciasDTO, existCarSeries, existCity, existInty, newCustomerRequest);

        List<CustomerInscDoc> ciasDocs = this.customerInscDocService.fileCheck(files, entityId);
        cias.setCustomerInscDoc(ciasDocs);

        List<CustomerInscExtend> ciasCuexs = this.customerInscExtendService.getCustomerInscEtend(cuexIds, cias, entityId, cias.getCiasCurrentPrice());


        BigDecimal premiPrice = this.customerInscAssetsService.getPremiPrice(
                existInty.getIntyName(),
                existCarSeries.getCarModel().getCarBrand().getCabrName(),
                existCity.getProvinsi().getZones().getZonesId(),
                ciasDTO.getCurrentPrice(),
                customer.getUserBirthDate().getYear(),
                ciasCuexs
        );

        cias.setCiasTotalPremi(premiPrice);
        cias.setCustomerInscExtend(ciasCuexs);

        CustomerClaim newClaim = this.customerClaimService.createNewClaim(newCustomerRequest);

        // set and save
        newCustomerRequest.setCustomerClaim(newClaim);
        newCustomerRequest.setCustomerInscAssets(cias);

        CustomerRequest savedCreq = this.customerRequestRepository.save(newCustomerRequest);
        log.info("CustomerRequestServiceImpl::create, successfully create customer request {} ", savedCreq);
        return TransactionMapper.mapEntityToDto(savedCreq, CustomerResponseDTO.class);
    }

    @Override
    public CustomerResponseDTO createByAgen(CreateCustomerRequestByAgenDTO customerRequestDTO, MultipartFile[] files) throws Exception {

        // prep
        CreateUserDto userPost = customerRequestDTO.getUserDTO();
        CiasDTO ciasDTO = customerRequestDTO.getCiasDTO();
        Long[] cuexIds = customerRequestDTO.getCiasDTO().getCuexIds();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime birthDate = LocalDateTime.parse(userPost.getProfile().getUserByAgenBirthDate(), formatter);

        if(this.customerInscAssetsService.isCiasAlreadyExist(ciasDTO.getCiasPoliceNumber())){
            throw new Exception("CustomerRequest with police number " + ciasDTO.getCiasPoliceNumber() + " is already exist");
        }

        userPost.getUserPhone().stream().forEach(
                userPhoneDto -> {
                    if(this.userPhoneService.findByPhoneNumber(userPhoneDto.getUserPhoneId().getUsphPhoneNumber()).isPresent()){
                        throw new UserPhoneExistException("User with phone number " + userPhoneDto.getUserPhoneId().getUsphPhoneNumber() + " is already exist");
                    }
                }
        );

        BusinessEntity newEntity = this.businessEntityService.createBusinessEntity();
        Long entityId = newEntity.getEntityId();


        CarSeries existCarSeries = this.carsService.getById(ciasDTO.getCiasCarsId());

        Cities existCity = this.cityService.getById(ciasDTO.getCiasCityId());

        InsuranceType existInty = this.intyService.getById(ciasDTO.getCiasIntyName());

        EmployeeAreaWorkgroup employeeAreaWorkgroup = this.employeeAreaWorkgroupRepository.findById(new EmployeeAreaWorkgroupId(customerRequestDTO.getAgenId(), customerRequestDTO.getEmployeeId()))
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee Areaworkgroup with id " + customerRequestDTO.getAgenId() + " is not found")
                );

        User newCustomer = this.createNewUserByAgen(userPost, birthDate, customerRequestDTO.getAccessGrantUser());


        CustomerRequest newCustomerRequest = this.createCustomerRequest(newEntity, newCustomer, entityId);
        newCustomerRequest.setCreqAgenEntityid(employeeAreaWorkgroup.getEawgId());
        newCustomerRequest.setEmployeeAreaWorkgroup(employeeAreaWorkgroup);

        CustomerInscAssets cias = this.customerInscAssetsService.createCustomerInscAssets(entityId, ciasDTO, existCarSeries, existCity, existInty, newCustomerRequest);

        List<CustomerInscDoc> ciasDocs = this.customerInscDocService.fileCheck(files, entityId);
        cias.setCustomerInscDoc(ciasDocs);

        List<CustomerInscExtend> ciasCuexs = this.customerInscExtendService.getCustomerInscEtend(cuexIds, cias, entityId, ciasDTO.getCurrentPrice());


        BigDecimal premiPrice = this.customerInscAssetsService.getPremiPrice(
                existInty.getIntyName(),
                existCarSeries.getCarModel().getCarBrand().getCabrName(),
                existCity.getProvinsi().getZones().getZonesId(),
                ciasDTO.getCurrentPrice(),
                birthDate.getYear(),
                ciasCuexs
        );

        cias.setCiasTotalPremi(premiPrice);
        cias.setCustomerInscExtend(ciasCuexs);

        CustomerClaim newClaim = this.customerClaimService.createNewClaim(newCustomerRequest);


        // set and save
        newCustomerRequest.setCustomerClaim(newClaim);
        newCustomerRequest.setCustomerInscAssets(cias);
        newCustomerRequest.setCustomer(newCustomer);

        CustomerRequest savedCreq = this.customerRequestRepository.save(newCustomerRequest);
        log.info("CustomerRequestServiceImpl::create, successfully create customer request {} ", savedCreq);
        return TransactionMapper.mapEntityToDto(savedCreq, CustomerResponseDTO.class);
    }


    @Override
    public Page<CustomerResponseDTO> getAllPaging(Pageable paging, String type, String status) {
        EnumCustomer.CreqStatus creqStatus = EnumCustomer.CreqStatus.valueOf(status);

        Page<CustomerRequest> pageCustomerRequest;

        if(Objects.equals(type, "ALL")){
            pageCustomerRequest = this.customerRequestRepository.findByCreqStatus(paging, creqStatus);
        }else{
            EnumCustomer.CreqType creqType = EnumCustomer.CreqType.valueOf(type);
            pageCustomerRequest = this.customerRequestRepository.findByCreqTypeAndCreqStatus(paging, creqType, creqStatus);
        }

        Page<CustomerResponseDTO> pageCustomerResponseDTO = pageCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        return pageCustomerResponseDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponseDTO> getPagingUserCustomerRequests(Long custId, Pageable paging, String type, String status) {
        User user = this.userService.getUserById(custId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + custId + " is not found")
        );

        EnumCustomer.CreqStatus creqStatus = EnumCustomer.CreqStatus.valueOf(status);

        Page<CustomerRequest> pageCustomerRequest;

        if(Objects.equals(type, "ALL")){
            pageCustomerRequest = this.customerRequestRepository.findByCustomerAndCreqStatus(user, paging, creqStatus);
        }else{
            EnumCustomer.CreqType creqType = EnumCustomer.CreqType.valueOf(type);
            pageCustomerRequest = this.customerRequestRepository.findByCustomerAndCreqTypeAndCreqStatus(user, paging, creqType, creqStatus);
        }

        Page<CustomerResponseDTO> pageCustomerResponseDTO = pageCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        log.info("CustomerRequestServiceImpl::getPagingCustomerRequest," +
                " successfully get all customer request who belong to user with ID: {}", user.getUserEntityId());

        return pageCustomerResponseDTO;
    }


    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponseDTO> getPagingAgenCustomerRequest(Long empId, String arwgCode, Pageable paging, String type, String status) {
        AreaWorkGroup existAreaWorkgroup = this.arwgService.getById(arwgCode);
        Employees existEmployee = this.employeesService.getById(empId);
        EmployeeAreaWorkgroup existEawg = this.employeeAreaWorkgroupRepository.findByAreaWorkGroupAndEmployees(existAreaWorkgroup, existEmployee).orElseThrow(
                () -> new EntityNotFoundException("Agen with id : " + empId + " is not found")
        );

        EnumCustomer.CreqStatus creqStatus = EnumCustomer.CreqStatus.valueOf(status);

        Page<CustomerRequest> pageCustomerRequest;

        if(Objects.equals(type, "ALL")){
            pageCustomerRequest = this.customerRequestRepository.findByEmployeeAreaWorkgroupAndCreqStatus(existEawg, paging, creqStatus);
        }else{
            EnumCustomer.CreqType creqType = EnumCustomer.CreqType.valueOf(type);
            pageCustomerRequest = this.customerRequestRepository.findByEmployeeAreaWorkgroupAndCreqTypeAndCreqStatus(existEawg, paging, creqType, creqStatus);
        }

        Page<CustomerResponseDTO> pageCustomerResponseDTO = pageCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        log.info("CustomerRequestServiceImpl::getPagingCustomerRequest," +
                " successfully get all customer request who belong to agen with ID: {} and areaCode: {}", empId, arwgCode);

        return pageCustomerResponseDTO;
    }

    @Transactional
    @Override
    public CustomerResponseDTO updateCustomerRequest(UpdateCustomerRequestDTO updateCustomerRequestDTO, MultipartFile[] files) throws Exception {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(updateCustomerRequestDTO.getCreqEntityId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Customer Request with id " + updateCustomerRequestDTO.getCreqEntityId() + " is not found")
                );


        Long entityId = existCustomerRequest.getBusinessEntity().getEntityId();
        CustomerInscAssets cias = existCustomerRequest.getCustomerInscAssets();

        CiasDTO ciasUpdateDTO = updateCustomerRequestDTO.getCiasDTO();
        Long[] cuexIds = ciasUpdateDTO.getCuexIds();


        User customer = this.getUpdatedUser(updateCustomerRequestDTO.getCustomerId(), updateCustomerRequestDTO.getAccessGrantUser());

        EmployeeAreaWorkgroup employeeAreaWorkgroup = this.employeeAreaWorkgroupRepository.findById(new EmployeeAreaWorkgroupId(updateCustomerRequestDTO.getAgenId(), updateCustomerRequestDTO.getEmployeeId()))
                .orElseThrow(
                        () -> new EntityNotFoundException("Employee Areaworkgroup with id " + updateCustomerRequestDTO.getAgenId() + " is not found")
                );


        existCustomerRequest.setCreqAgenEntityid(employeeAreaWorkgroup.getEawgId());
        existCustomerRequest.setCustomer(customer);


        CarSeries carSeries = this.carsService.getById(ciasUpdateDTO.getCiasCarsId());

        Cities existCity = this.cityService.getById(ciasUpdateDTO.getCiasCityId());

        InsuranceType existInty = this.intyService.getById(ciasUpdateDTO.getCiasIntyName());

        this.customerInscAssetsService.updateCustomerInscAssets(cias, ciasUpdateDTO, existCity, carSeries, existInty);

        // update cuex
        this.customerInscExtendService.deleteAllCustomerInscExtendInCustomerRequest(entityId);

        List<CustomerInscExtend> updatedCustomerInscExtend = this.customerInscExtendService.getCustomerInscEtend(cuexIds, cias, entityId, ciasUpdateDTO.getCurrentPrice());

        cias.setCustomerInscExtend(updatedCustomerInscExtend);

        // update cadoc
        this.customerInscDocService.deleteAllCustomerInscDocInCustomerRequest(entityId);

        List<CustomerInscDoc> newCiasDocs = this.customerInscDocService.fileCheck(files, entityId);
        cias.setCustomerInscDoc(newCiasDocs);


        BigDecimal premiPrice = this.customerInscAssetsService.getPremiPrice(
                existInty.getIntyName(),
                carSeries.getCarModel().getCarBrand().getCabrName(),
                existCity.getProvinsi().getZones().getZonesId(),
                ciasUpdateDTO.getCurrentPrice(),
                existCustomerRequest.getCustomer().getUserBirthDate().getYear(),
                updatedCustomerInscExtend
        );

        cias.setCiasTotalPremi(premiPrice);

        existCustomerRequest.setCreqModifiedDate(LocalDateTime.now());

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerRequest);
        log.info("CustomerRequestServiceImpl::updateCustomerRequest, successfully update customer request {}", savedCustomerRequest);
        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
        EmployeesAreaWorkgroupResponseDto eawagResponse = TransactionMapper.mapEntityToDto(employeeAreaWorkgroup, EmployeesAreaWorkgroupResponseDto.class);
        customerResponseDTO.setEmployeeAreaWorkgroup(eawagResponse);

        return customerResponseDTO;
    }

    @Override
    public User getUpdatedUser(Long userEntityId, Boolean grantUserAccess) {
        User customer = this.userService.getUserById(userEntityId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userEntityId + " is not found")
        );

        if(grantUserAccess){
            customer = this.userRolesService.updateRoleStatus(customer, "ACTIVE");
        }else{
            customer = this.userRolesService.updateRoleStatus(customer, "INACTIVE");
        }

        return customer;
    }

    @Transactional
    @Override
    public void delete(Long creqEntityId) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(creqEntityId).orElseThrow(
                () -> new EntityNotFoundException("Customer Request with id " + creqEntityId + " is not found")
        );

        this.customerRequestRepository.delete(existCustomerRequest);
        log.info("CustomerRequestServiceImpl:delete, successfully delete customer request");
    }

    @Override
    public CustomerRequest createCustomerRequest(
            BusinessEntity newEntity,
            User customer,
            Long entityId
    ){

        User updatedCustomer = this.userRolesService.updateRoleFromPcToCu(customer);


        CustomerRequest customerRequest = CustomerRequest.builder()
                .businessEntity(newEntity)
                .customer(updatedCustomer)
                .creqCreateDate(LocalDateTime.now())
                .creqStatus(EnumCustomer.CreqStatus.OPEN)
                .creqType(EnumCustomer.CreqType.FEASIBLITY)
                .creqEntityId(entityId)
                .build();

        log.info("CustomerRequestServiceImpl:createCustomerRequest, create new customerRequest");
        return customerRequest;
    }

    @Override
    public User createNewUserByAgen(CreateUserDto userPost, LocalDateTime birthDate, Boolean isActive){
        ProfileRequestDto profileRequestDto = userPost.getProfile();

        profileRequestDto.setUserName(userPost.getUserPhone().stream().findFirst().get().getUserPhoneId().getUsphPhoneNumber());
        profileRequestDto.setUserPassword(this.passwordEncoder.encode(userPost.getUserPhone().stream().findFirst().get().getUserPhoneId().getUsphPhoneNumber()));
        userPost.getProfile().setUserBirthDate(birthDate);
        User newCustomer = this.userService.createUserCustomerByAgen(userPost, isActive);
        return newCustomer;
    }


    @Transactional
    @Override
    public void changeRequestType(CustomerRequest customerRequest, EnumCustomer.CreqType creqType) {

        customerRequest.setCreqType(creqType);

        this.customerRequestRepository.save(customerRequest);
        log.info("CustomerRequestServiceImpl:changeRequestType, successfully change creq type to " + creqType.toString());
    }

    @Override
    public void changeRequestStatus(CustomerRequest customerRequest, EnumCustomer.CreqStatus creqStatus) {
        customerRequest.setCreqStatus(creqStatus);

        this.customerRequestRepository.save(customerRequest);
    }


}




