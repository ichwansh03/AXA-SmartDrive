package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;

    @Override
    public Services getById(Long aLong) {
        return soRepository.findById(aLong).get();
    }

    @Override
    public List<Services> getAll() {
        return soRepository.findAll();
    }

    @Override
    public Services save(Services services) {
        return soRepository.save(services);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Services addServices(Services services) {


        services = Services.builder()
//                .servCreatedOn(customerRequest.getCreqCreateDate())
//                .servType(customerRequest.getCreqType())
                .servInsuranceNo("temp")
                .servVehicleNumber("temp")
                .servCreatedOn(LocalDate.now())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(30))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .servServId(services.getServId())
//                .servCustEntityid(user.getUserEntityId())
//                .servCreqEntityid(customerRequest.getCreqEntityId())
                .build();

        return soRepository.save(services);
    }
}
