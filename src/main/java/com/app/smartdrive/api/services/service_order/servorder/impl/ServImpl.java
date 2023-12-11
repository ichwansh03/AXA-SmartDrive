package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.request.ServiceReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.impl.ServPremiImpl;
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
    private final SemiRepository semiRepository;
    private final SecrRepository secrRepository;

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
            case "POLIS" -> {
                Services servFs = soRepository.findByServTypeAndCustomer_CreqEntityId(EnumCustomer.CreqType.FEASIBLITY, cr.getCreqEntityId());
                log.info("Call ID CR {} ", servFs.getServId());
                serv = generatePolis(servFs.getServId(), cr);
            }
            case "CLAIM" -> {
                Services servPl = soRepository.findByServTypeAndCustomer_CreqEntityId(EnumCustomer.CreqType.POLIS, cr.getCreqEntityId());
                serv = generateClaim(servPl.getServId(), cr);
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

    private Services generatePolis(Long servId, CustomerRequest cr) {


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

        ServPremiImpl servPremi = new ServPremiImpl(semiRepository, secrRepository, soRepository);
        ServicePremi servicePremi = ServicePremi.builder()
                .semiServId(existingService.getServId())
                .semiPremiDebet(cr.getCustomerInscAssets().getCiasTotalPremi())
                .semiPaidType(cr.getCustomerInscAssets().getCiasPaidType().toString())
                .semiStatus("ACTIVE").build();

        servPremi.addSemi(servicePremi, existingService.getServId());

        return existingService;
    }

    private Services generateClaim(Long servId, CustomerRequest cr){
        Services existingService = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        existingService = Services.builder()
                .servId(existingService.getServId())
                .servType(cr.getCreqType())
                .servVehicleNumber(existingService.getServVehicleNumber())
                .servCreatedOn(LocalDateTime.now())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(10))
                .servInsuranceNo(soAdapter.generatePolisNumber(cr))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(existingService.getUsers())
                .customer(existingService.getCustomer()).build();

        return existingService;
    }

    private Services generateTypeInactive(CustomerRequest cr){
        return Services.builder()
                .servType(cr.getCreqType())
                .servCreatedOn(cr.getCreqCreateDate())
                .servStatus(EnumModuleServiceOrders.ServStatus.INACTIVE)
                .build();
    }
}
