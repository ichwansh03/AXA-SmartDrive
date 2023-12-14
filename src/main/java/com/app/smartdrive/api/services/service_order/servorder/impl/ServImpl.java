package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServImpl implements ServService {

    private final SoRepository soRepository;
    private final CustomerRequestRepository customerRequestRepository;
    private final ServOrderService servOrderService;
    private final ServPremiService servPremiService;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public Services addService(Long creqId) throws Exception {

        CustomerRequest cr = customerRequestRepository.findById(creqId).get();
        Services serv;

        switch (cr.getCreqType().toString()){
            case "FEASIBLITY" -> serv = generateFeasiblityType(cr);
            case "POLIS" -> serv = generatePolisType(cr);
            case "CLAIM" -> serv = generateClaimType(cr);
            default -> serv = generateTypeInactive(cr);
        }

        Services saved = soRepository.save(serv);
        log.info("ServOrderServiceImpl::addService created service");

        servOrderService.addServiceOrders(saved.getServId());

        log.info("ServOrderServiceImpl::addService created Service Orders");
        soRepository.flush();

        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public Services findServicesById(Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + servId + " not found"));
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",services.getServId());
        return services;
    }

    private Services generateFeasiblityType(CustomerRequest cr){
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

    private Services generatePolisType(CustomerRequest cr) {
        Services existingService = soRepository.findById(cr.getServices().getServId())
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        existingService = Services.builder()
                .servId(existingService.getServId())
                .servType(cr.getCreqType())
                .servVehicleNumber(existingService.getServVehicleNumber())
                .servCreatedOn(LocalDateTime.now())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusYears(1))
                .servInsuranceNo(soAdapter.generatePolis(cr))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(existingService.getUsers())
                .customer(existingService.getCustomer()).build();

        log.info("ServImpl::updateService successfully updated");

        generateServPremi(existingService, cr);

        return existingService;
    }

    private Services generateClaimType(CustomerRequest cr){
        Services existingService = soRepository.findById(cr.getServices().getServId())
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        existingService = Services.builder()
                .servId(existingService.getServId())
                .servType(cr.getCreqType())
                .servVehicleNumber(existingService.getServVehicleNumber())
                .servCreatedOn(LocalDateTime.now())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(10))
                .servInsuranceNo(soAdapter.generatePolis(cr))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(existingService.getUsers())
                .customer(existingService.getCustomer()).build();

        return existingService;
    }

    private Services generateTypeInactive(CustomerRequest cr){
        Services existingService = soRepository.findById(cr.getServices().getServId())
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        servPremiService.updateSemiStatus("INACTIVE", existingService.getServId());

        return Services.builder()
                .servId(existingService.getServId())
                .servType(cr.getCreqType())
                .servCreatedOn(cr.getCreqCreateDate())
                .servStatus(EnumModuleServiceOrders.ServStatus.INACTIVE)
                .users(existingService.getUsers())
                .customer(existingService.getCustomer())
                .build();
    }

    private void generateServPremi(Services services, CustomerRequest cr){
        ServicePremi servicePremi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(cr.getCustomerInscAssets().getCiasTotalPremi())
                .semiPaidType(cr.getCustomerInscAssets().getCiasPaidType().toString())
                .semiStatus(EnumModuleServiceOrders.SemiStatus.UNPAID.toString()).build();

        servPremiService.addSemi(servicePremi, services.getServId());
    }
}
