package com.app.smartdrive.api.services.customer.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.entities.customer.*;
import com.app.smartdrive.api.dto.user.response.BussinessEntityResponseDTO;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroupId;
import com.app.smartdrive.api.entities.master.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.customer.CustomerClaimRepository;
import com.app.smartdrive.api.repositories.customer.CustomerInscDocRepository;
import com.app.smartdrive.api.repositories.customer.CustomerInscExtendRepository;
import com.app.smartdrive.api.repositories.master.*;
import com.app.smartdrive.api.services.customer.*;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerRequestServiceImpl implements CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;

    private final CarsRepository carsRepository;

    private final IntyRepository intyRepository;

    private final CityRepository cityRepository;

    private final UserRepository userRepository;

    private final TemiRepository temiRepository;

    private final EmployeeAreaWorkgroupRepository employeeAreaWorkgroupRepository;

    private final BusinessEntityService businessEntityService;

    private final UserService userService;

    private final CustomerInscAssetsService customerInscAssetsService;

    private final CustomerInscDocService customerInscDocService;

    private final CustomerInscExtendService customerInscExtendService;

    private final CustomerClaimService customerClaimService;


    @Transactional(readOnly = true)
    @Override
    public List<CustomerRequest> get(){
        return this.customerRequestRepository.findAll();
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

        return pageCustomerResponseDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponseDTO getCustomerRequestById(Long creqEntityId){
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(creqEntityId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Request dengan id ${creqEntityId} tidak ditemukan")
                );

        return TransactionMapper.mapEntityToDto(existCustomerRequest, CustomerResponseDTO.class);
    }

    @Transactional
    @Override
    public CustomerResponseDTO create(@Valid CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception {
        // prep
        CiasDTO ciasDTO = customerRequestDTO.getCiasDTO();
        Long[] cuexIds = customerRequestDTO.getCiasDTO().getCuexIds();

        BusinessEntity newEntity = this.businessEntityService.createBusinessEntity();
        Long entityId = newEntity.getEntityId();
        User entityUser = this.userService.getUserById(customerRequestDTO.getCustomerId()).get();
        CarSeries carSeries = this.carsRepository.findById(ciasDTO.getCiasCarsId()).get();
        Cities existCity = this.cityRepository.findById(ciasDTO.getCiasCityId()).get();
        InsuranceType existInty = this.intyRepository.findById(ciasDTO.getCiasIntyName()).get();

        EmployeeAreaWorkgroup employeeAreaWorkgroup = this.employeeAreaWorkgroupRepository.findById(new EmployeeAreaWorkgroupId(customerRequestDTO.getAgenId(), customerRequestDTO.getEmployeeId())).get();

        // new customerRequest
        // belum set eawag
        CustomerRequest newCustomerRequest = this.createCustomerRequest(newEntity, entityUser, entityId);
        newCustomerRequest.setEmployeeAreaWorkgroup(employeeAreaWorkgroup);
        newCustomerRequest.setCreqAgenEntityid(employeeAreaWorkgroup.getEawgId());

        CustomerInscAssets cias = this.customerInscAssetsService.createCustomerInscAssets(entityId, ciasDTO, carSeries, existCity, existInty, newCustomerRequest);

        List<CustomerInscDoc> ciasDocs = this.customerInscDocService.fileCheck(files, entityId);
        cias.setCustomerInscDoc(ciasDocs);


        List<CustomerInscExtend> ciasCuexs = this.customerInscExtendService.getCustomerInscEtend(cuexIds, cias, entityId);

        Double premi = ciasDTO.getCurrentPrice();
        cias.setCiasTotalPremi(premi);
        cias.setCustomerInscExtend(ciasCuexs);

        CustomerClaim newClaim = this.customerClaimService.createNewClaim(newCustomerRequest);

        // set and save
        newCustomerRequest.setCustomerClaim(newClaim);
        newCustomerRequest.setCustomerInscAssets(cias);

        CustomerRequest savedCreq = this.customerRequestRepository.save(newCustomerRequest);
        return TransactionMapper.mapEntityToDto(savedCreq, CustomerResponseDTO.class);
    }

    @Override
    public CustomerResponseDTO convert(CustomerRequest customerRequest){
        CustomerInscAssets cias = customerRequest.getCustomerInscAssets();
        List<CustomerInscExtend> cuexList = cias.getCustomerInscExtend();
        List<CustomerInscDoc> cadocList = cias.getCustomerInscDoc();
        User customer = customerRequest.getCustomer();
        InsuranceType insuranceType = cias.getInsuranceType();
        CarSeries carSeries = cias.getCarSeries();


//        Employees employee = eawag.getEmployees();
//        EmployeeAreaWorkgroup eawag = customerRequest.getEmployeeAreaWorkgroup();

        BussinessEntityResponseDTO bussinessEntityResponseDTO = BussinessEntityResponseDTO.builder()
                .entityId(customerRequest.getBusinessEntity().getEntityId())
                .entityModifiedDate(customerRequest.getBusinessEntity().getEntityModifiedDate())
                .build();


        List<CuexResponseDTO> cuexResponseDTOList = cuexList.stream().map(cuex -> new CuexResponseDTO(
                cuex.getCuexId(),
                cuex.getCuexCreqEntityid(),
                cuex.getCuexName(),
                cuex.getCuexTotalItem(),
                cuex.getCuexNominal()
        )).toList();

        List<CadocResponseDTO> cadocResponseDTOList = cadocList.stream().map(cadoc -> new CadocResponseDTO(
                cadoc.getCadocId(),
                cadoc.getCadocCreqEntityid(),
                cadoc.getCadocFilename(),
                cadoc.getCadocFiletype(),
                cadoc.getCadocFilesize(),
                cadoc.getCadocCategory(),
                cadoc.getCadocModifiedDate()
        )).toList();

        CitiesResponseDTO citiesResponseDTO = CitiesResponseDTO.builder()
                .cityId(cias.getCity().getCityId())
                .cityName(cias.getCity().getCityName())
                .provName(cias.getCity().getProvinsi().getProvName())
                .build();

        IntyResponseDTO intyResponseDTO = IntyResponseDTO.builder()
                .intyName(insuranceType.getIntyName())
                .intyDesc(insuranceType.getIntyDesc())
                .build();

        CarSeriesResponseDTO carSeriesResponseDTO = CarSeriesResponseDTO.builder()
                .carsId(carSeries.getCarsId())
                .carsPassenger(carSeries.getCarsPassenger())
                .carsName(carSeries.getCarsName())
                .build();

//        CiasResponseDTO ciasResponseDTO = CiasResponseDTO.builder()
//                .ciasCreqEntityid(cias.getCiasCreqEntityid())
//                .ciasEnddate(cias.getCiasEnddate())
//                .ciasStartdate(cias.getCiasStartdate())
//                .ciasYear(cias.getCiasYear())
//                .ciasCurrentPrice(cias.getCiasCurrentPrice())
//                .ciasInsurancePrice(cias.getCiasInsurancePrice())
//                .ciasTotalPremi(cias.getCiasTotalPremi())
//                .ciasIsNewChar(cias.getCiasIsNewChar())
//                .ciasPaidType(cias.getCiasPaidType())
//                .ciasPoliceNumber(cias.getCiasPoliceNumber())
//                .customerInscDoc(cadocResponseDTOList)
//                .customerInscExtend(cuexResponseDTOList)
//                .city(citiesResponseDTO)
//                .carSeriesResponseDTO(carSeriesResponseDTO)
//                .intyResponseDTO(intyResponseDTO)
//                .build();


        List<UserPhoneResponseDTO> userPhoneResponseDTOList = customer.getUserPhone().stream().map(phone -> new UserPhoneResponseDTO(
                phone.getUserPhoneId().getUsphEntityId(),
                phone.getUserPhoneId().getUsphPhoneNumber(),
                phone.getUsphMime(),
                phone.getUsphPhoneType(),
                phone.getUsphStatus(),
                phone.getUsphModifiedDate()
        )).toList();

        // Address and Phone User
        List<UserAddressResponseDTO> addressUserResponseDTOList = customer.getUserAddress().stream().map(address -> new UserAddressResponseDTO(
                address.getUsdrId(),
                address.getUsdrEntityId(),
                address.getUsdrAddress1(),
                address.getUsdrAddress2(),
                address.getUsdrCityId(),
                address.getUsdrModifiedDate()
        )).toList();

        CustomerUserResponseDTO customerUserResponseDTO = CustomerUserResponseDTO.builder()
                .userEntityId(customer.getUserEntityId())
                .userName(customer.getUsername())
                .userFullName(customer.getUserFullName())
                .userBirthPlace(customer.getUserBirthPlace())
                .userNationalId(customer.getUserNationalId())
                .userNPWP(customer.getUserNPWP())
                .userBirthDate(customer.getUserBirthDate())
                .userModifiedDate(customer.getUserModifiedDate())
                .userEmail(customer.getUserEmail())
                .userPhoto(customer.getUserPhoto())
                .userAddresses(addressUserResponseDTOList)
                .userPhone(userPhoneResponseDTOList)
                .build();

//        //  Address and Phone Agen
//        List<UserPhoneResponseDTO> agenPhoneResponseDTOList = employee.getUser().getUserPhone().stream().map(phone -> new UserPhoneResponseDTO(
//                phone.getUserPhoneId().getUsphEntityId(),
//                phone.getUserPhoneId().getUsphPhoneNumber(),
//                phone.getUsphMime(),
//                phone.getUsphPhoneType(),
//                phone.getUsphStatus(),
//                phone.getUsphModifiedDate()
//        )).toList();
//
//        List<UserAddressResponseDTO> addressAgenResponseDTOList = employee.getUser().getUserAddress().stream().map(address -> new UserAddressResponseDTO(
//                address.getUsdrId(),
//                address.getUsdrEntityId(),
//                address.getUsdrAddress1(),
//                address.getUsdrAddress2(),
//                address.getUsdrCityId(),
//                address.getUsdrModifiedDate()
//        )).toList();
//
//        CustomerUserResponseDTO agenUserResponseDTO = CustomerUserResponseDTO.builder()
//                .userEntityId(employee.getUser().getUserEntityId())
//                .userName(employee.getUser().getUserName())
//                .userFullName(employee.getUser().getUserFullName())
//                .userBirthPlace(employee.getUser().getUserBirthPlace())
//                .userNationalId(employee.getUser().getUserNationalId())
//                .userNPWP(employee.getUser().getUserNPWP())
//                .userBirthDate(employee.getUser().getUserBirthDate())
//                .userModifiedDate(employee.getUser().getUserModifiedDate())
//                .userEmail(employee.getUser().getUserEmail())
//                .userPhoto(employee.getUser().getUserPhoto())
//                .userAddresses(addressAgenResponseDTOList)
//                .userPhone(agenPhoneResponseDTOList)
//                .build();
//
//        // EAWAG
//        EmployeeAreaWorkgroupDto employeeAreaWorkgroupDto = EmployeeAreaWorkgroupDto.builder()
//                .empName(eawag.getEmployees().getEmpName())
//                .cityName(eawag.getAreaWorkGroup().getCities().getCityName())
//                .provinsi(eawag.getAreaWorkGroup().getCities().getProvinsi().getProvName())
//                .zoneName(eawag.getAreaWorkGroup().getCities().getProvinsi().getZones().getZonesName())
//                .build();
//
//                // .workGroup(eawag.getAreaWorkGroup().getArwgCode())



//        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
//                .creqEntityId(customerRequest.getCreqEntityId())
//                .bussinessEntity(bussinessEntityResponseDTO)
//                .creqModifiedDate(customerRequest.getCreqModifiedDate())
//                .creqCreateDate(customerRequest.getCreqCreateDate())
//                .creqStatus(customerRequest.getCreqStatus())
//                .creqType(customerRequest.getCreqType())
//                .customerInscAssets(ciasResponseDTO)
//                .customer(customerUserResponseDTO)
//                .build();


            CustomerClaim customerClaim = customerRequest.getCustomerClaim();

            ClaimResponseDTO claimResponseDTO = ClaimResponseDTO.builder()
                    .cuclCreqEntityId(customerClaim.getCuclCreqEntityid())
                    .cuclCreateDate(customerClaim.getCuclCreateDate())
                    .cuclEvents(customerClaim.getCuclEvents())
                    .cuclEventPrice(customerClaim.getCuclEventPrice())
                    .cuclReason(customerClaim.getCuclReason())
                    .cuclSubtotal(customerClaim.getCuclSubtotal())
                    .build();

//            customerResponseDTO.setClaimResponseDTO(claimResponseDTO);


//                .employee(agenUserResponseDTO)
//                .employeeAreaWorkgroup(employeeAreaWorkgroupDto)
        return new CustomerResponseDTO();
    }

    @Transactional(readOnly = true)
    @Override
    public Double getPremiPrice(String insuraceType, String carBrand, Long zonesId, Double currentPrice, List<CustomerInscExtend> cuexs){
        TemplateInsurancePremi temiMain = this.temiRepository.findByTemiZonesIdAndTemiIntyNameAndTemiCateId(zonesId, insuraceType, 1L);

        Double premiMain = currentPrice * temiMain.getTemiRateMin();

        Double premiExtend = 0.0;

        if(!cuexs.isEmpty()){
            for (CustomerInscExtend  cuex: cuexs) {
                premiExtend += cuex.getCuexNominal();
            }

        }

        Double totalPremi = premiMain + premiExtend;

        return totalPremi;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CustomerResponseDTO> getPagingUserCustomerRequests(Long custId, Pageable paging, String type, String status) {
        User user = this.userRepository.findById(custId).get();
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

        return pageCustomerResponseDTO;
    }

    @Transactional
    @Override
    public CustomerResponseDTO updateCustomerRequest(Long creqEntityId, UpdateCustomerRequestDTO updateCustomerRequestDTO, MultipartFile[] files) throws Exception {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(creqEntityId)
                .orElseThrow(() -> new EntityNotFoundException("Customer Request dengan id " + creqEntityId + " tidak ditemukan")
                );

        Long entityId = existCustomerRequest.getBusinessEntity().getEntityId();
        CustomerInscAssets cias = existCustomerRequest.getCustomerInscAssets();

        CiasDTO ciasUpdateDTO = updateCustomerRequestDTO.getCiasDTO();
        Long[] cuexIds = ciasUpdateDTO.getCuexIds();


        CarSeries carSeries = this.carsRepository.findById(ciasUpdateDTO.getCiasCarsId()).orElseThrow(
                () -> new EntityNotFoundException("Car Series dengan id " + creqEntityId + " tidak ditemukan")
        );
        Cities existCity = this.cityRepository.findById(ciasUpdateDTO.getCiasCityId()).orElseThrow(
                () -> new EntityNotFoundException("City dengan id " + creqEntityId + " tidak ditemukan")
        );
        InsuranceType existInty = this.intyRepository.findById(ciasUpdateDTO.getCiasIntyName()).orElseThrow(
                () -> new EntityNotFoundException("Insurance type dengan id " + creqEntityId + " tidak ditemukan")
        );

        this.customerInscAssetsService.updateCustomerInscAssets(cias, ciasUpdateDTO, existCity, carSeries, existInty);


        // update cuex
        this.customerInscExtendService.deleteAllCustomerInscExtendInCustomerRequest(entityId);

        List<CustomerInscExtend> updatedCustomerInscExtend = this.customerInscExtendService.getCustomerInscEtend(cuexIds, cias, entityId);

        cias.setCustomerInscExtend(updatedCustomerInscExtend);

        // update cadoc
        this.customerInscDocService.deleteAllCustomerInscDocInCustomerRequest(entityId);

        List<CustomerInscDoc> newCiasDocs = this.customerInscDocService.fileCheck(files, entityId);
        cias.setCustomerInscDoc(newCiasDocs);

        existCustomerRequest.setCreqModifiedDate(LocalDateTime.now());

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerRequest);
        return TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
    }

    @Transactional
    @Override
    public void delete(Long creqEntityId) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(creqEntityId).orElseThrow(
                () -> new EntityNotFoundException("Customer Request dengan id " + creqEntityId + " tidak ditemukan")
        );

        this.customerRequestRepository.delete(existCustomerRequest);
    }

    @Override
    public CustomerRequest createCustomerRequest(
            BusinessEntity newEntity,
            User customer,
            Long entityId
    ){

        return CustomerRequest.builder()
                .businessEntity(newEntity)
                .customer(customer)
                .creqCreateDate(LocalDateTime.now())
                .creqStatus(EnumCustomer.CreqStatus.OPEN)
                .creqType(EnumCustomer.CreqType.FEASIBLITY)
                .creqEntityId(entityId)
                .build();
    }

    @Transactional
    @Override
    public void changeRequestTypeToPolis(CustomerRequestTypeDTO customerRequestTypeDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(customerRequestTypeDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Request dengan id " + customerRequestTypeDTO.getCreqEntityId() + " tidak ada")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.POLIS);

        this.customerRequestRepository.save(existCustomerRequest);
    }

    @Transactional
    @Override
    public void changeRequestTypeToClaim(CustomerRequestTypeDTO customerRequestTypeDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(customerRequestTypeDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Request dengan id " + customerRequestTypeDTO.getCreqEntityId() + " tidak ada")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.CLAIM);

        this.customerRequestRepository.save(existCustomerRequest);
    }


    @Transactional
    @Override
    public void changeRequestTypeToClose(CustomerRequestTypeDTO customerRequestTypeDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(customerRequestTypeDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Request dengan id " + customerRequestTypeDTO.getCreqEntityId() + " tidak ada")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.CLOSE);

        this.customerRequestRepository.save(existCustomerRequest);
    }


}




