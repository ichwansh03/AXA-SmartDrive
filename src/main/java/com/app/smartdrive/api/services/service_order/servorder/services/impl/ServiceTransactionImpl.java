package com.app.smartdrive.api.services.service_order.servorder.services.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.response.ServiceDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.services.ServiceTransaction;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServiceOrderTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceTransactionImpl implements ServiceTransaction {

    private final SoRepository soRepository;
    private final ServiceOrderTransaction serviceOrderTransaction;
    private final ServPremiService servPremiService;
    private final CustomerRequestRepository customerRequestRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceDto addService(Long creqId) {
        CustomerRequest cr = customerRequestRepository.findById(creqId)
                .orElseThrow(() -> new EntityNotFoundException("creqId " + creqId + " is not found"));

        Services serv;
        switch (cr.getCreqType().toString()) {
            case "FEASIBLITY" -> {
                serv = buildCommonServiceData(cr, LocalDateTime.now().plusDays(7),
                        EnumModuleServiceOrders.ServStatus.ACTIVE);
                customerRequestRepository.updateCreqType(EnumCustomer.CreqType.POLIS, cr.getCreqEntityId());
            }
            case "POLIS" -> {
                serv = handleServiceUpdate(cr, LocalDateTime.now().plusYears(1), EnumModuleServiceOrders.ServStatus.ACTIVE);
                log.info("ServImpl::addService save services to db polis {} ", serv);
            }
            case "CLAIM" -> serv = handleServiceUpdate(cr, LocalDateTime.now().plusDays(10), EnumModuleServiceOrders.ServStatus.ACTIVE);
            default -> serv = handleServiceUpdate(cr, LocalDateTime.now().plusDays(1), EnumModuleServiceOrders.ServStatus.INACTIVE);
        }

        log.info("ServImpl::addService save services to db {} ", serv);
        Services saved = soRepository.save(serv);
        log.info("ServImpl::addService service saved {} ", saved);

        serviceOrderTransaction.addServiceOrders(saved.getServId());

        soRepository.flush();
        log.info("ServOrderServiceImpl::addService sync data to db");

        return TransactionMapper.mapEntityToDto(saved, ServiceDto.class);
    }

    @Override
    public Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus) {
        Services existingService = soRepository.findById(cr.getServices().getServId())
                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));

        Services services = buildCommonServiceData(cr, endDate, servStatus);
        services.setServId(existingService.getServId());

        switch (services.getServType()) {
            case POLIS -> servPremiService.generateServPremi(services);
            case CLOSE -> servPremiService.updateSemiStatus(
                    EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), services.getServId());
        }
        log.info("service new existing {} ",services);

        return services;
    }


    @Override
    public Services buildCommonServiceData(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus){
        Services serviceParent = soRepository.getServiceParent(cr.getCustomer().getUserEntityId())
                .orElseThrow(() -> new EntityNotFoundException("Service Parent is not found"));

        return Services.builder()
                .servType(cr.getCreqType())
                .servCreatedOn(cr.getCreqCreateDate())
                .servInsuranceno(soAdapter.generatePolis(cr))
                .servVehicleno(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servStartdate(LocalDateTime.now())
                .servEnddate(endDate)
                .servStatus(servStatus)
                .users(cr.getCustomer())
                .parentServices(serviceParent)
                .customer(cr)
                .build();
    }
}
