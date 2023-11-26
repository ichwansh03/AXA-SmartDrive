package com.app.smartdrive.api.services.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.implementation.SoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.dto.customer.request.AgenDTO;
import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.AgenResponseDTO;
import com.app.smartdrive.api.dto.customer.response.BussinessEntityResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CadocResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CiasResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CuexResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.master.CarSeriesDto;
import com.app.smartdrive.api.dto.master.InsuranceTypeDto;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.customer.EnumCustomer.CadocCategory;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.master.CarsRepository;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.repositories.master.IntyRepository;
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

    private final EmployeesRepository employeesRepository;

    public List<CustomerRequest> get(){
        return this.customerRequestRepository.findAll();
    }

    public CustomerRequest create(@Valid CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception {
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
        
        // new customer
        CustomerRequest newCustomer = CustomerRequest.builder()
        .businessEntity(existEntity)
        .customer(entityUser)
        .creqCreateDate(LocalDateTime.now())
        .creqStatus(EnumCustomer.CreqStatus.OPEN)
        .creqType(EnumCustomer.CreqType.POLIS)
        .creqEntityId(entityId)
        .build();
        
        // new cias
        CustomerInscAssets cias = CustomerInscAssets.builder()
        .ciasCreqEntityid(entityId)
        .ciasPoliceNumber(ciasDTO.getCiasPoliceNumber())
        .ciasYear(ciasDTO.getCiasYear())
        .ciasStartdate(LocalDateTime.now())
        .ciasEnddate(LocalDateTime.now().plusYears(1))
        .ciasCurrentPrice(1_000_000.00)
        .ciasTotalPremi(50_000_000.00)
        .ciasPaidType(EnumCustomer.CreqPaidType.valueOf(ciasDTO.getCiasPaidType()))
        .ciasIsNewChar(ciasDTO.getCiasIsNewChar())
        .carSeries(carSeries)
        .city(existCity)
        .insuranceType(existInty)
        .customerRequest(newCustomer)
        .build();

        // new cuex(sementara nunggu tengku)
        CustomerInscExtend cuex = CustomerInscExtend.builder()
        .cuexName("Perlindungan dan kerugian pihak ketiga")
        .cuexTotalItem(1)
        .cuex_nominal(155_000_000)
        .customerInscAssets(cias)
        .cuexCreqEntityid(entityId)
        .build();

        // pengecekan dan covert file upload ke cadocList
        List<CustomerInscDoc> ciasDocs = this.fileCheck(files, entityId);
        cias.setCustomerInscDoc(ciasDocs);

        // set cias ke creq
        List<CustomerInscExtend> ciasCuexs = new ArrayList<>();
        ciasCuexs.add(cuex);
        cias.setCustomerInscExtend(ciasCuexs);

        // set cias ke customer
        newCustomer.setCustomerInscAssets(cias);

        // ikhwan generate
        SoServiceImpl service = new SoServiceImpl(soRepository);
        service.addServices(newCustomer, cias, entityUser, entityId);

        return this.customerRequestRepository.save(newCustomer);
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

    public CustomerRequest setAgenCreq(AgenDTO agenDTO) {
        CustomerRequest customerRequest = this.customerRequestRepository.findById(agenDTO.getCreqId()).get();
        Employees agen = this.employeesRepository.findById(agenDTO.getAgenId()).get();

        customerRequest.setEmployee(agen);
        
        List<CustomerRequest> creqList = agen.getCustomerRequests();
        creqList.add(customerRequest);

        Employees savedAgen = this.employeesRepository.save(agen);

        Optional<CustomerRequest> savedCreq = savedAgen.getCustomerRequests().stream()
        .filter(creq -> 
            creq.getCreqEntityId().equals(customerRequest.getCreqEntityId())
        ).findFirst();

        return savedCreq.orElse(null);
    }

    public CustomerResponseDTO convert(CustomerRequest customerRequest){

        CustomerInscAssets cias = customerRequest.getCustomerInscAssets();
        List<CustomerInscDoc> cadoc = cias.getCustomerInscDoc();
        List<CustomerInscExtend> cuex = cias.getCustomerInscExtend();
        BusinessEntity businessEntity = customerRequest.getBusinessEntity();
        CarSeries carSeries = cias.getCarSeries();
        InsuranceType insuranceType = cias.getInsuranceType();

        // List<UserAddress> userAddress = agen.getUser().getUserAddress();
        // List<Cities> cityList = userAddress.stream().map(address -> address.getCity()).toList();

        BussinessEntityResponseDTO bussinessEntityResponseDTO = BussinessEntityResponseDTO.builder()
        .entityId(businessEntity.getEntityId())
        .entityModifiedDate(businessEntity.getEntityModifiedDate())
        .build();

        CarSeriesDto carSeriesDto = CarSeriesDto.builder()
        .carsCarmId(carSeries.getCarsCarmId())
        .carsId(carSeries.getCarsId())
        .carsName(carSeries.getCarsName())
        .carsPassenger(carSeries.getCarsPassenger())
        .build();

        InsuranceTypeDto insuranceTypeDto = InsuranceTypeDto.builder()
        .intyName(insuranceType.getIntyName())
        .intyDesc(insuranceType.getIntyDesc())
        .build();

        List<CadocResponseDTO> cadocListDTO = cadoc.stream().map(doc -> new CadocResponseDTO(
            doc.getCadocCreqEntityid(), doc.getCadocFilename(),
            doc.getCadocFiletype(),
            doc.getCadocFilesize(),
            doc.getCadocCategory(),
            doc.getCadocModifiedDate())
        ).toList();

        List<CuexResponseDTO> cuexListDTO = cuex.stream().map(extend -> new CuexResponseDTO(
            extend.getCuexId(),
            extend.getCuexCreqEntityid(),
            extend.getCuexName(),
            extend.getCuexTotalItem(),
            extend.getCuex_nominal()
        )).toList();

        CiasResponseDTO ciasResponseDTO = CiasResponseDTO.builder()
        .ciasCreqEntityid(cias.getCiasCreqEntityid())
        .ciasCurrentPrice(cias.getCiasCurrentPrice())
        .ciasStartdate(cias.getCiasStartdate())
        .ciasEnddate(cias.getCiasEnddate())
        .ciasIsNewChar(cias.getCiasIsNewChar())
        .ciasPaidType(cias.getCiasPaidType())
        .ciasPoliceNumber(cias.getCiasPoliceNumber())
        .ciasTotalPremi(cias.getCiasTotalPremi())
        .ciasYear(cias.getCiasYear())
        .city(cias.getCity().getCityName())
        .cuexResponseDTOList(cuexListDTO)
        .cadocResponseDTOList(cadocListDTO)
        .carSeriesDto(carSeriesDto)
        .insuranceTypeDto(insuranceTypeDto)
        .build();


        CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
        .creqEntityId(customerRequest.getCreqEntityId())
        .businessEntityResponseDTO(bussinessEntityResponseDTO)
        .creqCreateDate(customerRequest.getCreqCreateDate())
        .creqStatus(customerRequest.getCreqStatus())
        .creqType(customerRequest.getCreqType())
        .customer(customerRequest.getCustomer())
        .ciasResponseDTO(ciasResponseDTO)
        .build();

        return responseDTO;
    }
}
