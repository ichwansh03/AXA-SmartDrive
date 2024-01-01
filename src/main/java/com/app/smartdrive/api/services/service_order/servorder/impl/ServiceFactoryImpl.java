package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.response.ServiceDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceFactory;
import com.app.smartdrive.api.services.service_order.servorder.ServiceOrderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceFactoryImpl implements ServiceFactory {

    private final SoRepository soRepository;
    private final ServiceOrderFactory serviceOrderFactory;
    private final ServPremiService servPremiService;
    private final CustomerRequestRepository customerRequestRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceDto addService(Long creqId) throws Exception {

        CustomerRequest cr = customerRequestRepository.findById(creqId)
                .orElseThrow(() -> new EntityNotFoundException("creqId "+creqId+" is not found"));

        Services serv;

        switch (cr.getCreqType().toString()){
            case "FEASIBLITY" -> {
                serv = buildCommonServiceData(cr, LocalDateTime.now().plusDays(7),
                        EnumModuleServiceOrders.ServStatus.ACTIVE);
                customerRequestRepository.updateCreqType(EnumCustomer.CreqType.POLIS, cr.getCreqEntityId());
            }
            case "POLIS" -> {
                serv = handleServiceUpdate(cr,
                        LocalDateTime.now().plusYears(1), EnumModuleServiceOrders.ServStatus.ACTIVE);
                log.info("ServImpl::addService save services to db polis {} ",serv);
            }
            case "CLAIM" -> serv = handleServiceUpdate(cr,
                    LocalDateTime.now().plusDays(10), EnumModuleServiceOrders.ServStatus.ACTIVE);
            default -> serv = handleServiceUpdate(cr,
                    LocalDateTime.now().plusDays(1), EnumModuleServiceOrders.ServStatus.INACTIVE);
        }

        log.info("ServImpl::addService save services to db {} ",serv);
        Services saved = soRepository.save(serv);
        log.info("ServImpl::addService service saved {} ",saved);

        serviceOrderFactory.addServiceOrders(saved.getServId());

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
        Services serviceParent = soRepository.getServiceParent(cr.getCustomer().getUserEntityId())
                .orElse(null);

        return Services.builder()
                .servType(cr.getCreqType())
                .servCreatedOn(cr.getCreqCreateDate())
                .servInsuranceNo(soAdapter.generatePolis(cr))
                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
                .servStartDate(LocalDateTime.now())
                .servEndDate(endDate)
                .servStatus(servStatus)
                .users(cr.getCustomer())
                .parentServices(serviceParent)
                .customer(cr)
                .build();
    }
}
