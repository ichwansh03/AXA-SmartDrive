package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.services.customer.CustomerInscAssetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerInscAssetsServiceImpl implements CustomerInscAssetsService {
    @Override
    public CustomerInscAssets createCustomerInscAssets(
            Long entityId,
            CiasDTO ciasDTO,
            CarSeries carSeries,
            Cities existCity,
            InsuranceType existInty,
            CustomerRequest newCustomerRequest
    ){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ciasStartdate = LocalDateTime.parse(ciasDTO.getCiasStartdate(), formatter);

        // new cias

        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
                .ciasCreqEntityid(entityId).ciasPoliceNumber(ciasDTO.getCiasPoliceNumber())
                .ciasYear(ciasDTO.getCiasYear())
                .ciasStartdate(ciasStartdate)
                .ciasEnddate(ciasStartdate.plusYears(1))
                .ciasCurrentPrice(ciasDTO.getCurrentPrice())
                .ciasInsurancePrice(ciasDTO.getCurrentPrice())
                .ciasPaidType(EnumCustomer.CreqPaidType.valueOf(ciasDTO.getCiasPaidType()))
                .ciasIsNewChar(ciasDTO.getCiasIsNewChar())
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
            CiasDTO ciasUpdateDTO,
            Cities existCity,
            CarSeries carSeries,
            InsuranceType existInty
    ){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ciasStartdate = LocalDateTime.parse(ciasUpdateDTO.getCiasStartdate(), formatter);

        cias.setCiasPoliceNumber(ciasUpdateDTO.getCiasPoliceNumber());
        cias.setCiasYear(ciasUpdateDTO.getCiasYear());
        cias.setCiasStartdate(ciasStartdate);
        cias.setCiasEnddate(ciasStartdate.plusYears(1));
        cias.setCiasCurrentPrice(ciasUpdateDTO.getCurrentPrice());
        cias.setCiasTotalPremi(ciasUpdateDTO.getCurrentPrice());
        cias.setCiasPaidType(EnumCustomer.CreqPaidType.valueOf(ciasUpdateDTO.getCiasPaidType()));
        cias.setCiasIsNewChar(ciasUpdateDTO.getCiasIsNewChar());
        cias.setCity(existCity);
        cias.setCarSeries(carSeries);
        cias.setInsuranceType(existInty);

        log.info("CustomerInscAssetsServiceImpl::updateCustomerInscAssets, update customerInscAssets by ID : {}", cias.getCiasCreqEntityid());
    }


}
