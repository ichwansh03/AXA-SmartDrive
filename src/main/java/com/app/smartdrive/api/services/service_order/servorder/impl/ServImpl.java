package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServImpl implements ServService {

    private final SoRepository soRepository;
    private final ServOrderService servOrderService;
    private final ServOrderTaskService servOrderTaskService;
    private final ServOrderWorkorderService servOrderWorkorderService;

    private final ServPremiService servPremiService;
    private final ServPremiCreditService servPremiCreditService;

    private final CustomerRequestRepository customerRequestRepository;
    private final CustomerRequestService customerRequestService;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public Services addService(Long creqId) throws Exception {

        CustomerRequest cr = customerRequestRepository.findById(creqId)
                .orElseThrow(() -> new EntityNotFoundException("addService(Long creqId)::creqId "+creqId+" is not found"));
        Services serv;

        switch (cr.getCreqType().toString()){
            case "FEASIBLITY" -> serv = generateFeasiblityType(cr);
            case "POLIS" -> serv = handleServiceUpdate(cr,
                    LocalDateTime.now().plusYears(1), EnumModuleServiceOrders.ServStatus.ACTIVE);
            case "CLAIM" -> serv = handleServiceUpdate(cr,
                    LocalDateTime.now().plusDays(10), EnumModuleServiceOrders.ServStatus.ACTIVE);
            default -> serv = handleServiceUpdate(cr,
                    LocalDateTime.now(), EnumModuleServiceOrders.ServStatus.INACTIVE);
        }

        if(Objects.equals(cr.getCreqType().toString(), "CLOSE")){
            cr.setCreqStatus(EnumCustomer.CreqStatus.CLOSED);
        }

        Services saved = soRepository.save(serv);
        log.info("ServOrderServiceImpl::addService save services to db");

        servOrderService.addServiceOrders(saved.getServId());

        soRepository.flush();
        log.info("ServOrderServiceImpl::addService sync data to db");

        return saved;
    }

    @Transactional(readOnly = true)
    @Override
    public ServiceRespDto findServicesById(Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + servId + " not found"));
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",services.getServId());

        CustomerResponseDTO customerRequestById = customerRequestService.getCustomerRequestById(services.getCustomer().getCreqEntityId());
        List<ServiceOrders> allSeroByServId = servOrderService.findAllSeroByServId(services.getServId());

        List<ServiceOrderRespDto> serviceOrderRespDtos = allSeroByServId.stream()
                .map(serviceOrders -> {
                    ServiceOrderRespDto dto = TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class);
                    List<SoTasksDto> soTasksDtoList = servOrderTaskService.findSeotBySeroId(dto.getSeroId()).stream()
                            .map(seot -> {
                                SoTasksDto soTasksDto = TransactionMapper.mapEntityToDto(seot, SoTasksDto.class);
                                List<ServiceOrderWorkorder> sowoBySeotId = servOrderWorkorderService.findSowoBySeotId(soTasksDto.getSeotId());
                                soTasksDto.setServiceOrderWorkorders(TransactionMapper.mapEntityListToDtoList(sowoBySeotId, SoWorkorderDto.class));
                                return soTasksDto;
                            })
                            .collect(toList());
                    dto.setSoTasksDtoList(soTasksDtoList);
                    return dto;
                })
                .collect(Collectors.toList());

        ServicePremi servicePremi = servPremiService.findByServId(services.getServId());
        SemiDto semiDto = TransactionMapper.mapEntityToDto(servicePremi, SemiDto.class);
        List<SecrDto> secrDtoList = servPremiCreditService.findByServId(services.getServId()).stream()
                .map(secr -> TransactionMapper.mapEntityToDto(secr, SecrDto.class))
                .collect(Collectors.toList());
        semiDto.setSecrDtoList(secrDtoList);

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);
        serviceRespDto.setCustomerResponseDTO(customerRequestById);
        serviceRespDto.setServiceOrdersList(serviceOrderRespDtos);
        serviceRespDto.setSemiDto(semiDto);

        return serviceRespDto;
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

    private Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus) {
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

        return existingService;
    }

    private void generateServPremi(Services services){
        ServicePremi servicePremi = ServicePremi.builder()
                .semiServId(services.getServId())
                .semiPremiDebet(services.getCustomer().getCustomerInscAssets().getCiasTotalPremi())
                .semiPaidType(services.getCustomer().getCustomerInscAssets().getCiasPaidType().toString())
                .semiStatus(EnumModuleServiceOrders.SemiStatus.UNPAID.toString()).build();

        servPremiService.addSemi(servicePremi, services.getServId());
    }

}
