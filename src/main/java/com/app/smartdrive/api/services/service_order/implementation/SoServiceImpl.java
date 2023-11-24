package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.service_order.SoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;

    @Override
    public CustomerRequest findCreqById(Long id) {

        return soRepository.findByCreqEntityid(id);
    }

    @Override
    public Services addServices(CustomerRequest customerRequest, CustomerInscAssets customerInscAssets, User user) {
        Services services = Services.builder()
                .servCreatedOn(customerRequest.getCreqCreateDate())
                .servType(customerRequest.getCreqType())
                .servVehicleNumber(customerInscAssets.getCiasPoliceNumber())
                .servStartDate(customerInscAssets.getCiasStartdate())
                .servEndDate(customerInscAssets.getCiasEnddate())
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .servCustEntityid(user.getUserEntityId())
                .servCreqEntityid(customerRequest.getCreqEntityId())
                .build();

        services.setServServId(services.getServId());

        return soRepository.save(services);
    }

}
