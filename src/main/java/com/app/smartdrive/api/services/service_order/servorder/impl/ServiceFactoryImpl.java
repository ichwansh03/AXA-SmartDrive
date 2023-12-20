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
        return buildCommonServiceData(cr, LocalDateTime.now().plusDays(7), EnumModuleServiceOrders.ServStatus.ACTIVE);
    }

    @Override
    public Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus) {
        Services existingService = soRepository.findById(cr.getServices().getServId())
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        Services services = buildCommonServiceData(cr, endDate, servStatus);
        services.setServId(existingService.getServId());

        switch (services.getServType()) {
            case POLIS -> generateServPremi(services);
            case CLOSE -> servPremiService.updateSemiStatus(
                    EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), services.getServId());
        }
        log.info("service new existing {} ",services);

        return services;
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

    private Services buildCommonServiceData(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus){
        return Services.builder()
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
    }
}
