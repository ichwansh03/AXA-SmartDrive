package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.Exceptions.*;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.customer.request.CustomerInscAssetsRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CreateCustomerRequestByAgenDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.request.UpdateCustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.entities.customer.*;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.EnumUsers;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.customer.*;
import com.app.smartdrive.api.services.master.ArwgService;
import com.app.smartdrive.api.services.master.CarsService;
import com.app.smartdrive.api.services.master.CityService;
import com.app.smartdrive.api.services.master.IntyService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserRolesService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerRequestServiceImpl implements CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;

    private final EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;

    private final EmployeeAreaWorkgroupService employeeAreaWorkgroupService;

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

    // sementara
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponseDTO> get(){
        List<CustomerRequest> customerRequestList = this.customerRequestRepository.findAll();
        log.info("CustomerRequestServiceImpl::get, get all customer request");
        return TransactionMapper.mapEntityListToDtoList(customerRequestList, CustomerResponseDTO.class);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerRequest getById(Long creqEntityId){

        return this.customerRequestRepository.findById(creqEntityId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Customer Request with id " + creqEntityId + " is not found")
                );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerRequest> getAllPaging(Pageable paging, String type, String status) {
        EnumCustomer.CreqStatus creqStatus = EnumCustomer.CreqStatus.valueOf(status);

        Page<CustomerRequest> pageCustomerRequest;

        if(Objects.equals(type, "ALL")){
            pageCustomerRequest = this.customerRequestRepository.findByCreqStatus(paging, creqStatus);
        }else{
            EnumCustomer.CreqType creqType = EnumCustomer.CreqType.valueOf(type);
            pageCustomerRequest = this.customerRequestRepository.findByCreqTypeAndCreqStatus(paging, creqType, creqStatus);
        }

        log.info("CustomerRequestServiceImpl::getAllPaging get paging all customer request");
        return pageCustomerRequest;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerRequest> getPagingUserCustomerRequest(Long customerId, Pageable paging, String type, String status) {
        User user = this.userService.getUserById(customerId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + customerId + " is not found")
        );

        EnumCustomer.CreqStatus creqStatus = EnumCustomer.CreqStatus.valueOf(status);

        Page<CustomerRequest> pageCustomerRequest;

        if(Objects.equals(type, "ALL")){
            pageCustomerRequest = this.customerRequestRepository.findByCustomerAndCreqStatus(user, paging, creqStatus);
        }else{
            EnumCustomer.CreqType creqType = EnumCustomer.CreqType.valueOf(type);
            pageCustomerRequest = this.customerRequestRepository.findByCustomerAndCreqTypeAndCreqStatus(user, paging, creqType, creqStatus);
        }


        log.info("CustomerRequestServiceImpl::getPagingUserCustomerRequest," +
                " successfully get all customer request who belong to user with ID: {}", user.getUserEntityId());

        return pageCustomerRequest;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerRequest> getPagingAgenCustomerRequest(Long employeeId, String arwgCode, Pageable paging, String type, String status) {
        AreaWorkGroup existAreaWorkgroup = this.arwgService.getById(arwgCode);
        Employees existEmployee = this.employeesService.getById(employeeId);
        EmployeeAreaWorkgroup existEmployeeAreaworkgroup = this.employeeAreaWorkgroupRepository.findByAreaWorkGroupAndEmployees(existAreaWorkgroup, existEmployee).orElseThrow(
                () -> new EntityNotFoundException("Agen with id : " + employeeId + " is not found")
        );

        EnumCustomer.CreqStatus creqStatus = EnumCustomer.CreqStatus.valueOf(status);

        Page<CustomerRequest> pageCustomerRequest;

        if(Objects.equals(type, "ALL")){
            pageCustomerRequest = this.customerRequestRepository.findByEmployeeAreaWorkgroupAndCreqStatus(existEmployeeAreaworkgroup, paging, creqStatus);
        }else{
            EnumCustomer.CreqType creqType = EnumCustomer.CreqType.valueOf(type);
            pageCustomerRequest = this.customerRequestRepository.findByEmployeeAreaWorkgroupAndCreqTypeAndCreqStatus(existEmployeeAreaworkgroup, paging, creqType, creqStatus);
        }


        log.info("CustomerRequestServiceImpl::getPagingAgenCustomerRequest," +
                " successfully get all customer request who belong to agen with ID: {} and areaCode: {}", employeeId, arwgCode);

        return pageCustomerRequest;
    }

    @Transactional
    @Override
    public CustomerRequest create(CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception {
        // prep
        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = customerRequestDTO.getCustomerInscAssetsRequestDTO();
        Long[] customerInscExtendIds = customerRequestDTO.getCustomerInscAssetsRequestDTO().getCuexIds();

        this.customerInscAssetsService.validatePoliceNumber(customerInscAssetsRequestDTO.getCiasPoliceNumber());

        User existCustomer = this.userService.getById(customerRequestDTO.getCustomerId());
        CarSeries existCarSeries = this.carsService.getById(customerInscAssetsRequestDTO.getCiasCarsId());
        Cities existCity = this.cityService.getById(customerInscAssetsRequestDTO.getCiasCityId());
        InsuranceType existInsuranceType = this.intyService.getById(customerInscAssetsRequestDTO.getCiasIntyName());
        EmployeeAreaWorkgroup existEmployeeAreaWorkgroup = this.employeeAreaWorkgroupService.getById(
                customerRequestDTO.getAgenId(), customerRequestDTO.getEmployeeId()
        );


        BusinessEntity newBusinessEntity = this.businessEntityService.createBusinessEntity();
        Long entityId = newBusinessEntity.getEntityId();


        CustomerRequest newCustomerRequest = this.createCustomerRequest(newBusinessEntity, existCustomer, entityId);
        newCustomerRequest.setCreqAgenEntityid(existEmployeeAreaWorkgroup.getEawgId());
        newCustomerRequest.setEmployeeAreaWorkgroup(existEmployeeAreaWorkgroup);

        CustomerClaim newCustomerClaim = this.customerClaimService.createNewClaim(newCustomerRequest);

        CustomerInscAssets newCustomerInscAssets = this.customerInscAssetsService.createCustomerInscAssets(
                entityId, customerInscAssetsRequestDTO, existCarSeries, existCity, existInsuranceType, newCustomerRequest
        );
        List<CustomerInscDoc> newCustomerInscDocList = this.customerInscDocService.fileCheck(files, entityId);
        List<CustomerInscExtend> newCustomerInscExtendList = this.customerInscExtendService.getCustomerInscEtend(
                customerInscExtendIds, newCustomerInscAssets, entityId, newCustomerInscAssets.getCiasCurrentPrice()
        );


        BigDecimal premiPrice = this.customerInscAssetsService.getPremiPrice(
                existInsuranceType.getIntyName(),
                existCarSeries.getCarModel().getCarBrand().getCabrName(),
                existCity.getProvinsi().getZones().getZonesId(),
                customerInscAssetsRequestDTO.getCurrentPrice(),
                existCustomer.getUserBirthDate().getYear(),
                newCustomerInscExtendList
        );


        // cias set
        newCustomerInscAssets.setCiasTotalPremi(premiPrice);
        newCustomerInscAssets.setCustomerInscExtend(newCustomerInscExtendList);
        newCustomerInscAssets.setCustomerInscDoc(newCustomerInscDocList);

        // set and save
        newCustomerRequest.setCustomerClaim(newCustomerClaim);
        newCustomerRequest.setCustomerInscAssets(newCustomerInscAssets);

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(newCustomerRequest);
        log.info("CustomerRequestServiceImpl::create, successfully create customer request {} ", savedCustomerRequest);
        return savedCustomerRequest;
    }

    @Transactional
    @Override
    public CustomerRequest createByAgen(CreateCustomerRequestByAgenDTO customerRequestDTO, MultipartFile[] files) throws Exception {

        // prep
        CreateUserDto createUserDto = customerRequestDTO.getUserDTO();
        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = customerRequestDTO.getCustomerInscAssetsRequestDTO();
        Long[] customerInscExtendIds = customerRequestDTO.getCustomerInscAssetsRequestDTO().getCuexIds();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime birthDate = LocalDateTime.parse(createUserDto.getProfile().getUserByAgenBirthDate(), formatter);

        this.customerInscAssetsService.validatePoliceNumber(customerInscAssetsRequestDTO.getCiasPoliceNumber());

        if(userRepository.existsByUserName((createUserDto.getUserPhone().stream().findFirst().get().getUserPhoneId().getUsphPhoneNumber()))){
            throw new UsernameExistException();
        }

        if(userRepository.existsByUserEmail(createUserDto.getProfile().getUserEmail())){
            throw new EmailExistException();
        };

        if(userRepository.existsByUserNPWP(createUserDto.getProfile().getUserNpwp())){
            throw new UserExistException("User with NPWP number " + createUserDto.getProfile().getUserNpwp() + " is already exist");
        }

        if(userRepository.existsByUserNationalId(createUserDto.getProfile().getUserNationalId())){
            throw new UserExistException("User with national id " + createUserDto.getProfile().getUserNationalId() + " is already exist");
        }

        createUserDto.getUserPhone().forEach(
                phone -> {
                    if(userPhoneService.findByPhoneNumber(phone.getUserPhoneId().getUsphPhoneNumber()).isPresent())
                        throw new UserPhoneExistException(phone.getUserPhoneId().getUsphPhoneNumber());
                }
        );

        CarSeries existCarSeries = this.carsService.getById(customerInscAssetsRequestDTO.getCiasCarsId());
        Cities existCity = this.cityService.getById(customerInscAssetsRequestDTO.getCiasCityId());
        InsuranceType existInsuranceType = this.intyService.getById(customerInscAssetsRequestDTO.getCiasIntyName());
        EmployeeAreaWorkgroup existEmployeeAreaWorkgroup = this.employeeAreaWorkgroupService.getById(
                customerRequestDTO.getAgenId(), customerRequestDTO.getEmployeeId()
        );
        User newCustomer = this.createNewUserByAgen(createUserDto, birthDate, customerRequestDTO.getAccessGrantUser());


        BusinessEntity newBusinessEntity = this.businessEntityService.createBusinessEntity();
        Long entityId = newBusinessEntity.getEntityId();


        CustomerRequest newCustomerRequest = this.createCustomerRequestByAgen(newBusinessEntity, newCustomer, entityId);
        newCustomerRequest.setCreqAgenEntityid(existEmployeeAreaWorkgroup.getEawgId());
        newCustomerRequest.setEmployeeAreaWorkgroup(existEmployeeAreaWorkgroup);

        CustomerInscAssets newCustomerInscAssets = this.customerInscAssetsService.createCustomerInscAssets(
                entityId, customerInscAssetsRequestDTO, existCarSeries, existCity, existInsuranceType, newCustomerRequest
        );
        List<CustomerInscExtend> newCustomerInscExtendList = this.customerInscExtendService.getCustomerInscEtend(
                customerInscExtendIds, newCustomerInscAssets, entityId, customerInscAssetsRequestDTO.getCurrentPrice()
        );
        List<CustomerInscDoc> newCustomerInscDocList = this.customerInscDocService.fileCheck(files, entityId);


        BigDecimal premiPrice = this.customerInscAssetsService.getPremiPrice(
                existInsuranceType.getIntyName(),
                existCarSeries.getCarModel().getCarBrand().getCabrName(),
                existCity.getProvinsi().getZones().getZonesId(),
                customerInscAssetsRequestDTO.getCurrentPrice(),
                birthDate.getYear(),
                newCustomerInscExtendList
        );

        newCustomerInscAssets.setCiasTotalPremi(premiPrice);
        newCustomerInscAssets.setCustomerInscDoc(newCustomerInscDocList);
        newCustomerInscAssets.setCustomerInscExtend(newCustomerInscExtendList);

        CustomerClaim newCustomerClaim = this.customerClaimService.createNewClaim(newCustomerRequest);


        // set and save
        newCustomerRequest.setCustomerClaim(newCustomerClaim);
        newCustomerRequest.setCustomerInscAssets(newCustomerInscAssets);
        newCustomerRequest.setCustomer(newCustomer);

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(newCustomerRequest);
        log.info("CustomerRequestServiceImpl::create, successfully create customer request {} ", savedCustomerRequest);
        return savedCustomerRequest;
    }

    @Transactional
    @Override
    public CustomerRequest updateCustomerRequest(UpdateCustomerRequestDTO updateCustomerRequestDTO, MultipartFile[] files) throws Exception {
        CustomerRequest existCustomerRequest = this.getById(updateCustomerRequestDTO.getCreqEntityId());
        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = updateCustomerRequestDTO.getCustomerInscAssetsRequestDTO();
        Long[] customerInscExtendIds = customerInscAssetsRequestDTO.getCuexIds();

        Long entityId = existCustomerRequest.getBusinessEntity().getEntityId();
        CustomerInscAssets existCustomerInscAssets = existCustomerRequest.getCustomerInscAssets();


        User existCustomer = this.getUpdatedUser(updateCustomerRequestDTO.getCustomerId(), updateCustomerRequestDTO.getAccessGrantUser());

        EmployeeAreaWorkgroup existEmployeeAreaWorkgroup = this.employeeAreaWorkgroupService.getById(
                updateCustomerRequestDTO.getAgenId(), updateCustomerRequestDTO.getEmployeeId()
        );

        CarSeries existCarSeries = this.carsService.getById(customerInscAssetsRequestDTO.getCiasCarsId());
        Cities existCity = this.cityService.getById(customerInscAssetsRequestDTO.getCiasCityId());
        InsuranceType existInsuranceType = this.intyService.getById(customerInscAssetsRequestDTO.getCiasIntyName());



        // update cuex
        this.customerInscExtendService.deleteAllCustomerInscExtendInCustomerRequest(entityId);

        List<CustomerInscExtend> updatedCustomerInscExtend = this.customerInscExtendService.getCustomerInscEtend(
                customerInscExtendIds, existCustomerInscAssets, entityId, customerInscAssetsRequestDTO.getCurrentPrice()
        );

        existCustomerInscAssets.setCustomerInscExtend(updatedCustomerInscExtend);

        // update cadoc
        this.customerInscDocService.deleteAllCustomerInscDocInCustomerRequest(entityId);

        List<CustomerInscDoc> newCiasDocs = this.customerInscDocService.fileCheck(files, entityId);
        existCustomerInscAssets.setCustomerInscDoc(newCiasDocs);


        BigDecimal premiPrice = this.customerInscAssetsService.getPremiPrice(
                existInsuranceType.getIntyName(),
                existCarSeries.getCarModel().getCarBrand().getCabrName(),
                existCity.getProvinsi().getZones().getZonesId(),
                customerInscAssetsRequestDTO.getCurrentPrice(),
                existCustomerRequest.getCustomer().getUserBirthDate().getYear(),
                updatedCustomerInscExtend
        );

        // update cias
        this.customerInscAssetsService.updateCustomerInscAssets(existCustomerInscAssets, customerInscAssetsRequestDTO, existCity, existCarSeries, existInsuranceType);
        existCustomerInscAssets.setCiasTotalPremi(premiPrice);

        existCustomerRequest.setCreqAgenEntityid(existEmployeeAreaWorkgroup.getEawgId());
        existCustomerRequest.setCustomer(existCustomer);
        existCustomerRequest.setCreqModifiedDate(LocalDateTime.now());

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerRequest);
        log.info("CustomerRequestServiceImpl::updateCustomerRequest, successfully update customer request {}", savedCustomerRequest);
//        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
//        EmployeesAreaWorkgroupResponseDto eawagResponse = TransactionMapper.mapEntityToDto(existEmployeeAreaWorkgroup, EmployeesAreaWorkgroupResponseDto.class);
//        customerResponseDTO.setEmployeeAreaWorkgroup(eawagResponse);

        return savedCustomerRequest;
    }

    @Transactional
    @Override
    public User getUpdatedUser(Long userEntityId, Boolean grantUserAccess) {
        User customer = this.userService.getUserById(userEntityId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userEntityId + " is not found")
        );

        this.userRolesService.updateUserRoleStatus(customer.getUserEntityId(), EnumUsers.RoleName.CU, grantUserAccess? "ACTIVE":"INACTIVE");

        return userService.getById(customer.getUserEntityId());
    }

    @Transactional
    @Override
    public CustomerRequest createCustomerRequest(
            BusinessEntity newEntity,
            User customer,
            Long entityId
    ){

        this.userRolesService.updateRoleFromPcToCu(customer.getUserEntityId());
        User updatedCustomer = userService.getUserById(customer.getUserEntityId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

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

    @Transactional
    @Override
    public CustomerRequest createCustomerRequestByAgen(
            BusinessEntity newEntity,
            User customer,
            Long entityId
    ){

        CustomerRequest customerRequest = CustomerRequest.builder()
                .businessEntity(newEntity)
                .customer(customer)
                .creqCreateDate(LocalDateTime.now())
                .creqStatus(EnumCustomer.CreqStatus.OPEN)
                .creqType(EnumCustomer.CreqType.FEASIBLITY)
                .creqEntityId(entityId)
                .build();

        log.info("CustomerRequestServiceImpl:createCustomerRequest, create new customerRequest");
        return customerRequest;
    }

    @Transactional
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

    @Transactional
    @Override
    public void changeRequestStatus(CustomerRequest customerRequest, EnumCustomer.CreqStatus creqStatus) {
        customerRequest.setCreqStatus(creqStatus);

        this.customerRequestRepository.save(customerRequest);
    }

    @Transactional
    @Override
    public void delete(Long creqEntityId) {
        CustomerRequest existCustomerRequest = this.getById(creqEntityId);

        this.customerRequestRepository.delete(existCustomerRequest);
        log.info("CustomerRequestServiceImpl:delete, successfully delete customer request");
    }
}




