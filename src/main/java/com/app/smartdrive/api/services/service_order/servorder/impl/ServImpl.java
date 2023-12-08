package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServImpl implements ServService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
    private final CustomerRequestRepository customerRequestRepository;
    private final TestaRepository testaRepository;
    private final TewoRepository tewoRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public Services addService(Long creqId) throws Exception {

        CustomerRequest cr = customerRequestRepository.findById(creqId).get();
        Services serv;
        ServOrderImpl servOrder = new ServOrderImpl(soRepository, soOrderRepository, soTasksRepository,
                soWorkorderRepository, testaRepository, tewoRepository);

        switch (cr.getCreqType().toString()){
            case "FEASIBLITY" -> serv = generateFeasiblity(cr);
            case "POLIS", "CLAIM" -> {
                Services servFs = soRepository.findByServTypeAndCustomer_CreqEntityId(EnumCustomer.CreqType.FEASIBLITY, cr.getCreqEntityId());
                log.info("Call ID CR {} ", servFs.getServId());
                serv = generatePolis(servFs.getServId(), cr);
            }
            default -> serv = generateTypeInactive(cr);
        }

        Services saved = soRepository.save(serv);
        log.info("ServOrderServiceImpl::addService created service");

        servOrder.addServiceOrders(saved.getServId());

        log.info("ServOrderServiceImpl::addService created Service Orders");
        soRepository.flush();

        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Services> findServicesById(Long servId) {
        Optional<Services> byId = soRepository.findById(servId);
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",byId);
        return byId;
    }

    public Services generateFeasiblity(CustomerRequest cr){
        return Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servCreatedOn(cr.getCreqCreateDate())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(7))
                .users(cr.getCustomer())
                .customer(cr)
                .build();
    }

    public Services generatePolis(Long servId, CustomerRequest cr) {

        Services existingService = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        existingService = Services.builder()
                .servId(existingService.getServId())
                .servType(cr.getCreqType())
                .servVehicleNumber(existingService.getServVehicleNumber())
                .servCreatedOn(LocalDateTime.now())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusYears(1))
                .servInsuranceNo(soAdapter.generatePolisNumber(cr))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(existingService.getUsers())
                .customer(existingService.getCustomer()).build();

        log.info("ServImpl::updateService successfully updated");

        return existingService;
    }

    public Services generateTypeInactive(CustomerRequest cr){
        return Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servInsuranceNo(soAdapter.generatePolisNumber(cr))
                .servCreatedOn(cr.getCreqCreateDate())
                .servStatus(EnumModuleServiceOrders.ServStatus.INACTIVE)
                .users(cr.getCustomer())
                .customer(cr)
                .parentServices(generateFeasiblity(cr))
                .build();
    }
}
