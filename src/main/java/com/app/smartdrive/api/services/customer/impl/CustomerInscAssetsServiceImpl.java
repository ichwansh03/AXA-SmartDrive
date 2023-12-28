package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.Exceptions.EntityAlreadyExistException;
import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.CustomerInscAssetsRequestDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.master.*;
import com.app.smartdrive.api.repositories.customer.CustomerInscAssetsRepository;
import com.app.smartdrive.api.repositories.master.TemiRepository;
import com.app.smartdrive.api.services.customer.CustomerInscAssetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerInscAssetsServiceImpl implements CustomerInscAssetsService {

    private final TemiRepository temiRepository;

    private final CustomerInscAssetsRepository customerInscAssetsRepository;

    @Override
    public CustomerInscAssets createCustomerInscAssets(
            Long entityId,
            CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO,
            CarSeries carSeries,
            Cities existCity,
            InsuranceType existInty,
            CustomerRequest newCustomerRequest
    ){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ciasStartdate = LocalDateTime.parse(customerInscAssetsRequestDTO.getCiasStartdate(), formatter);

        // new cias

        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
                .ciasCreqEntityid(entityId).ciasPoliceNumber(customerInscAssetsRequestDTO.getCiasPoliceNumber())
                .ciasYear(customerInscAssetsRequestDTO.getCiasYear())
                .ciasStartdate(ciasStartdate)
                .ciasEnddate(ciasStartdate.plusYears(1))
                .ciasCurrentPrice(customerInscAssetsRequestDTO.getCurrentPrice())
                .ciasInsurancePrice(customerInscAssetsRequestDTO.getCurrentPrice())
                .ciasPaidType(EnumCustomer.CreqPaidType.valueOf(customerInscAssetsRequestDTO.getCiasPaidType()))
                .ciasIsNewChar(customerInscAssetsRequestDTO.getCiasIsNewChar())
                .carSeries(carSeries)
                .city(existCity)
                .insuranceType(existInty)
                .customerRequest(newCustomerRequest)
                .build();

        log.info("CustomerInscAssetsServiceImpl::createCustomerInscAssets, create new customerInscAssets");
        return customerInscAssets;
    }

    @Override
    public void updateCustomerInscAssets(
            CustomerInscAssets cias,
            CustomerInscAssetsRequestDTO ciasUpdateDTO,
            Cities existCity,
            CarSeries carSeries,
            InsuranceType existInty
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ciasStartdate = LocalDateTime.parse(ciasUpdateDTO.getCiasStartdate(), formatter);

        if(!Objects.equals(cias.getCiasPoliceNumber(), ciasUpdateDTO.getCiasPoliceNumber())){
            cias.setCiasPoliceNumber(ciasUpdateDTO.getCiasPoliceNumber());
        }

        cias.setCiasYear(ciasUpdateDTO.getCiasYear());
        cias.setCiasStartdate(ciasStartdate);
        cias.setCiasEnddate(ciasStartdate.plusYears(1));
        cias.setCiasCurrentPrice(ciasUpdateDTO.getCurrentPrice());
        cias.setCiasInsurancePrice(ciasUpdateDTO.getCurrentPrice());
        cias.setCiasPaidType(EnumCustomer.CreqPaidType.valueOf(ciasUpdateDTO.getCiasPaidType()));
        cias.setCiasIsNewChar(ciasUpdateDTO.getCiasIsNewChar());
        cias.setCity(existCity);
        cias.setCarSeries(carSeries);
        cias.setInsuranceType(existInty);

        log.info("CustomerInscAssetsServiceImpl::updateCustomerInscAssets, update customerInscAssets by ID : {}", cias.getCiasCreqEntityid());
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal getPremiPrice(String insuraceType, String carBrand, Long zonesId, BigDecimal currentPrice, int ageOfBirth, List<CustomerInscExtend> cuexs){

        List<String> carBrandRateMax = List.of("BMW", "MERCEDEZ BENZ", "AUDI", "VOLKSWAGEN", "LAND ROVER"
                , "JAGUAR", "PEUGOT", "RENAULT", "SMART", "VOLVO",
                "MINI", "FLAT", "OPEN", "MAZDA");

        TemplateInsurancePremi temiMain = this.temiRepository.findByTemiZonesIdAndTemiIntyNameAndTemiCateId(zonesId, insuraceType, 1L).orElseThrow(
                () -> new EntityNotFoundException("Template Insurance Premi is not found")
        );

        int yearsNow = LocalDateTime.now().getYear();
        int userAge = yearsNow - ageOfBirth;

        Double temiRate;

        if(userAge >= 17 && userAge <= 27){
            temiRate = temiMain.getTemiRateMax();
        }else {
            if (carBrandRateMax.contains(carBrand)){
                temiRate = temiMain.getTemiRateMax();
            }else{
                temiRate = temiMain.getTemiRateMin();
            }
        }
            

        Double rate = temiRate / 100;
        BigDecimal rateBig = BigDecimal.valueOf(rate);
        BigDecimal premiMain = currentPrice.multiply(rateBig);

        BigDecimal premiExtend = BigDecimal.valueOf(0);

        BigDecimal materai = BigDecimal.valueOf(10000);

        if(!cuexs.isEmpty()){
            for (CustomerInscExtend  cuex: cuexs) {
                premiExtend = premiExtend.add(cuex.getCuexNominal());
            }

        }

        BigDecimal totalPremi = premiMain.add(premiExtend);
        BigDecimal result = totalPremi.add(materai);

        log.info("CustomerRequestServiceImpl:getPremiPrice, successfully calculate premi");
        return result;
    }

    @Override
    public void validatePoliceNumber(String policeNumber){
        this.customerInscAssetsRepository.findByCiasPoliceNumber(policeNumber)
                .ifPresent(
                        customerInscAssets -> {
                            throw new EntityAlreadyExistException("Customer Request with police number " + policeNumber + " is already exist");
                        }
                );
    }

}
