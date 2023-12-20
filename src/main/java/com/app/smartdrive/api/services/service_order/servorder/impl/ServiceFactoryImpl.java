package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceFactoryImpl implements ServiceFactory {

    private final SoRepository soRepository;
    private final ServPremiService servPremiService;

    SoAdapter soAdapter = new SoAdapter();

    @Override
    public Services generateFeasiblityType(CustomerRequest cr){
        return Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servCreatedOn(cr.getCreqCreateDate())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(7))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(cr.getCustomer())
                .customer(cr)
                .build();
    }

    @Override
    public Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus) {
        Services existingService = soRepository.findById(cr.getServices().getServId())
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        existingService = Services.builder()
                .servId(existingService.getServId())
                .servType(cr.getCreqType())
                .servCreatedOn(cr.getCreqCreateDate())
                .servInsuranceNo(soAdapter.generatePolis(cr))
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servStartDate(LocalDateTime.now())
                .servEndDate(endDate)
                .servStatus(servStatus)
                .users(cr.getCustomer())
                .customer(cr)
                .build();

        switch (existingService.getServType()) {
            case POLIS -> generateServPremi(existingService);
            case CLOSE -> servPremiService.updateSemiStatus(
                    EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), existingService.getServId());
        }
        log.info("service new existing {} ",existingService);

        return existingService;
    }

    @Override
    public void generateServPremi(Services services){
        ServicePremi servicePremi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(services.getCustomer().getCustomerInscAssets().getCiasTotalPremi())
                .semiPaidType(services.getCustomer().getCustomerInscAssets().getCiasPaidType().toString())
                .semiStatus(EnumModuleServiceOrders.SemiStatus.UNPAID.toString()).build();
        log.info("service premi {} ", services);
        servPremiService.addSemi(servicePremi, services.getServId());
    }

}
