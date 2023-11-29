package com.app.smartdrive.api.services.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.implementation.ServOrderServiceImpl;
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
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
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
        ServOrderServiceImpl service = new ServOrderServiceImpl(soRepository, null, null, null);
        service.addServices(newCustomer, cias, entityUser, entityId);

        log.info("CustomerRequestServiceImpl::create successfully stored services {}",service);

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
}
