package com.app.smartdrive.api.services.customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.HR.EmployeesDto;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.dto.master.CitiesDto;
import com.app.smartdrive.api.dto.user.BussinessEntityResponseDTO;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.*;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.customer.CustomerInscExtendRepository;
import com.app.smartdrive.api.repositories.master.*;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.customer.EnumCustomer.CadocCategory;
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
public class CustomerRequestServiceImpl {
    private final CustomerRequestRepository customerRequestRepository;

    private final BusinessEntityRepository businessEntityRepo;

    private final CarsRepository carsRepository;

    private final IntyRepository intyRepository;

    private final CityRepository cityRepository;

    private final SoRepository soRepository;

    private final UserRepository userRepository;

    private final EmployeeAreaWorkgroupRepository eawagRepository;

    private final ArwgRepository arwgRepository;

    private final CustomerInscExtendRepository cuexRepository;

    private final TemiRepository temiRepository;


    public List<CustomerRequest> get(){
        return this.customerRequestRepository.findAll();
    }

    public CustomerResponseDTO create(@Valid CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception {
        CiasDTO ciasDTO = customerRequestDTO.getCiasDTO();

        // create new businessEntity
        BusinessEntity newEntity = new BusinessEntity();
        newEntity.setEntityModifiedDate(LocalDateTime.now());

        BusinessEntity existEntity = this.businessEntityRepo.saveAndFlush(newEntity);
        log.info("BusinessEntity created {}",existEntity);
        User entityUser = this.userRepository.findById(customerRequestDTO.getCreq_cust_entityid()).get();
        Long entityId = existEntity.getEntityId();

        // get from table master
        CarSeries carSeries = this.carsRepository.findById(ciasDTO.getCias_cars_id()).get();
        Cities existCity = this.cityRepository.findById(ciasDTO.getCias_city_id()).get();
        InsuranceType existInty = this.intyRepository.findById(ciasDTO.getCias_inty_name()).get();

//        EmployeeAreaWorkgroup eawag = this.eawagRepository.findByAgenAndArwgCode(customerRequestDTO.getAgen_id(), customerRequestDTO.getArwg_code());


        Long[] cuexIds = customerRequestDTO.getCiasDTO().getCuexIds();


        // new customer
        CustomerRequest newCustomer = CustomerRequest.builder()
        .businessEntity(existEntity)
        .customer(entityUser)
        .creqCreateDate(LocalDateTime.now())
        .creqStatus(EnumCustomer.CreqStatus.OPEN)
        .creqType(EnumCustomer.CreqType.POLIS)
        .creqEntityId(entityId)
        .build();

//        .employeeAreaWorkgroup(eawag)
//        eawag.setCustomerRequests(List.of(newCustomer));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ciasStartdate = LocalDateTime.parse(ciasDTO.getCiasStartdate(), formatter);



        // new cias
        CustomerInscAssets cias = CustomerInscAssets.builder()
        .ciasCreqEntityid(entityId)
        .ciasPoliceNumber(ciasDTO.getCiasPoliceNumber())
        .ciasYear(ciasDTO.getCiasYear())
        .ciasStartdate(ciasStartdate)
        .ciasEnddate(ciasStartdate.plusYears(1))
        .ciasCurrentPrice(ciasDTO.getCurrentPrice())
                .ciasInsurancePrice(ciasDTO.getCurrentPrice())
        .ciasPaidType(EnumCustomer.CreqPaidType.valueOf(ciasDTO.getCiasPaidType()))
        .ciasIsNewChar('Y')
        .carSeries(carSeries)
        .city(existCity)
        .insuranceType(existInty)
        .customerRequest(newCustomer)
        .build();


        // pengecekan dan covert file upload ke cadocList
        List<CustomerInscDoc> ciasDocs = this.fileCheck(files, entityId);
        cias.setCustomerInscDoc(ciasDocs);

        // set cias ke creq
        List<CustomerInscExtend> ciasCuexs = new ArrayList<>();

        for (Long i: cuexIds) {
            Double nominal;

            TemplateInsurancePremi temi = this.temiRepository.findById(i).get();

            if(Objects.nonNull(temi.getTemiRateMin())){
                nominal = temi.getTemiRateMin() * temi.getTemiNominal();
            }else{
                nominal = temi.getTemiNominal();
            }

            CustomerInscExtend cuex = CustomerInscExtend.builder()
                    .cuexName(temi.getTemiName())
                    .cuex_nominal(nominal)
                    .cuexTotalItem(1)
                    .customerInscAssets(cias)
                    .cuexCreqEntityid(entityId)
                    .build();

//                    .cuexId(temi.getTemiId())
            ciasCuexs.add(cuex);
        }

//        Double premi = this.getPremiPrice(existInty.getIntyName(), "bruh", eawag.getAreaWorkGroup().getCities().getProvinsi().getZones().getZonesId(), ciasDTO.getCurrentPrice(), ciasCuexs);

        Double premi = customerRequestDTO.getCiasDTO().getCurrentPrice();
        cias.setCiasTotalPremi(premi);
        cias.setCustomerInscExtend(ciasCuexs);

        // set cias ke customer
        newCustomer.setCustomerInscAssets(cias);


        CustomerRequest savedCreq = this.customerRequestRepository.save(newCustomer);
        return this.convert(savedCreq);
    }

    public List<CustomerInscDoc> fileCheck(MultipartFile[] files, Long creqEntityId) throws Exception {
        
        List<CustomerInscDoc> listDoc = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            String fileName = StringUtils.cleanPath(files[i].getOriginalFilename());
            CadocCategory category;
            
            if(i == 0){
                category = CadocCategory.KTP;
            }else if(i == 1){
                category = CadocCategory.SIUP;
            }else{
                category = CadocCategory.TDP;
            }
        
            try {

                if(fileName.contains("..")) {
                    throw  new Exception("Filename contains invalid path sequence " + fileName);
                }

                listDoc.add(CustomerInscDoc.builder()
                .cadocCreqEntityid(creqEntityId)
                .cadocFilename(fileName)
                .cadocFiletype(files[i].getContentType())
                .cadocFilesize((int) files[i].getSize())
                .cadocCategory(category)
                .cadocModifiedDate(LocalDateTime.now())
                .build());

                // file.transferTo(new File("C:\\Users\\E7450\\michael\\projects\\Java-Northwind-Backend\\src\\main\\resources\\images\\" + file.getOriginalFilename()));
            
            } catch (Exception e) {
                throw new Exception("Could not save File: " + fileName + " because : " + e.getMessage());
            }

        }

        return listDoc;
    }

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
                cuex.getCuex_nominal()
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

        CiasResponseDTO ciasResponseDTO = CiasResponseDTO.builder()
                .ciasCreqEntityid(cias.getCiasCreqEntityid())
                .ciasEnddate(cias.getCiasEnddate())
                .ciasStartdate(cias.getCiasStartdate())
                .ciasYear(cias.getCiasYear())
                .ciasCurrentPrice(cias.getCiasCurrentPrice())
                .ciasInsurancePrice(cias.getCiasInsurancePrice())
                .ciasTotalPremi(cias.getCiasTotalPremi())
                .ciasIsNewChar(cias.getCiasIsNewChar())
                .ciasPaidType(cias.getCiasPaidType())
                .ciasPoliceNumber(cias.getCiasPoliceNumber())
                .customerInscDoc(cadocResponseDTOList)
                .customerInscExtend(cuexResponseDTOList)
                .city(citiesResponseDTO)
                .carSeriesResponseDTO(carSeriesResponseDTO)
                .intyResponseDTO(intyResponseDTO)
                .build();


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
                .userName(customer.getUserName())
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

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(customerRequest.getCreqEntityId())
                .bussinessEntity(bussinessEntityResponseDTO)
                .creqModifiedDate(customerRequest.getCreqModifiedDate())
                .creqCreateDate(customerRequest.getCreqCreateDate())
                .creqStatus(customerRequest.getCreqStatus())
                .creqType(customerRequest.getCreqType())
                .customerInscAssets(ciasResponseDTO)
                .customer(customerUserResponseDTO)
                .build();

//                .employee(agenUserResponseDTO)
//                .employeeAreaWorkgroup(employeeAreaWorkgroupDto)
        return customerResponseDTO;
    }

    public Double getPremiPrice(String insuraceType, String carBrand, Long zonesId, Double currentPrice, List<CustomerInscExtend> cuexs){
        TemplateInsurancePremi temiMain = this.temiRepository.findByTemiZonesIdAndTemiIntyNameAndTemiCateId(zonesId, insuraceType, 1L);

        Double premiMain = currentPrice * temiMain.getTemiRateMin();

        Double premiExtend = 0.0;

        if(!cuexs.isEmpty()){
            for (CustomerInscExtend  cuex: cuexs) {
                premiExtend += cuex.getCuex_nominal();
            }

        }

        Double totalPremi = premiMain + premiExtend;

        return totalPremi;
    }

}
