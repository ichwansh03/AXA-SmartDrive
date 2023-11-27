package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoService;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;

    @Override
    public Services addServices(CustomerRequest customerRequest, CustomerInscAssets customerInscAssets, User user, Long creqId) {
        Services services = Services.builder()
                .servCreatedOn(customerRequest.getCreqCreateDate())
                .servType(customerRequest.getCreqType())
                .servVehicleNumber(customerInscAssets.getCiasPoliceNumber())
                .servStartDate(customerInscAssets.getCiasStartdate())
                .servEndDate(customerInscAssets.getCiasEnddate())
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .customer(customerRequest)
                .users(user)
                .servCreqEntityid(creqId)
                .servCustEntityid(user.getUserEntityId())
                .build();

        Services saveService = soRepository.save(services);

        services.setServServId(services.getServId());

        soRepository.flush();

        log.info("SoServiceImpl::addServices() successfully added {}",services);

        return saveService;
    }
}
