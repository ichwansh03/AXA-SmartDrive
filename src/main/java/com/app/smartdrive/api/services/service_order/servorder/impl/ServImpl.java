package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
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

    @Transactional
    @Override
    public Services addService(Long creqId) {

        SoAdapter soAdapter = new SoAdapter();
        CustomerRequest cr = customerRequestRepository.findById(creqId).get();

        Services serv = Services.builder()
                .servType(cr.getCreqType())
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servInsuranceNo(soAdapter.generatePolisNumber(cr))
                .servCreatedOn(cr.getCreqCreateDate())
                .servStartDate(LocalDateTime.now())
                .servEndDate(LocalDateTime.now().plusDays(7))
                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
                .users(cr.getCustomer())
                .customer(cr)
                .build();

        Services saved = soRepository.save(serv);
        log.info("ServOrderServiceImpl::addService created service");

        ServOrderImpl servOrder = new ServOrderImpl(soRepository, soOrderRepository, soTasksRepository, soWorkorderRepository);
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
}
