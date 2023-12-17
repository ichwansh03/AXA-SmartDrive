package com.smartdrive.serviceorderservice.services.servorder.impl;

import com.smartdrive.serviceorderservice.dto.response.*;
import com.smartdrive.serviceorderservice.entities.*;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import com.smartdrive.serviceorderservice.mapper.TransactionMapper;
import com.smartdrive.serviceorderservice.repositories.SoRepository;
import com.smartdrive.serviceorderservice.services.SoAdapter;
import com.smartdrive.serviceorderservice.services.premi.ServPremiCreditService;
import com.smartdrive.serviceorderservice.services.premi.ServPremiService;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderService;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderTaskService;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderWorkorderService;
import com.smartdrive.serviceorderservice.services.servorder.ServService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//    private final CustomerRequestRepository customerRequestRepository;
//    private final CustomerRequestService customerRequestService;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public Services addService(Long creqId) throws Exception {

        //CustomerRequest cr = customerRequestRepository.findById(creqId).get();
        Services serv = new Services();

//        switch (cr.getCreqType().toString()){
//            case "FEASIBLITY" -> serv = generateFeasiblityType(cr);
//            case "POLIS" -> serv = handleServiceUpdate(cr,
//                    LocalDateTime.now().plusYears(1), EnumModuleServiceOrders.ServStatus.ACTIVE);
//            case "CLAIM" -> serv = handleServiceUpdate(cr,
//                    LocalDateTime.now().plusDays(10), EnumModuleServiceOrders.ServStatus.ACTIVE);
//            default -> serv = handleServiceUpdate(cr,
//                    LocalDateTime.now().plusDays(1), EnumModuleServiceOrders.ServStatus.INACTIVE);
//        }

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

//        CustomerResponseDTO customerRequestById = customerRequestService.getCustomerRequestById(services.getCustomer().getCreqEntityId());
        List<ServiceOrders> allSeroByServId = servOrderService.findAllSeroByServId(services.getServId());
        List<ServiceOrderRespDto> serviceOrderRespDtos = allSeroByServId.stream()
                .map(serviceOrders -> TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class))
                .collect(Collectors.toList());

        for (ServiceOrderRespDto serviceOrderRespDto : serviceOrderRespDtos) {
            List<ServiceOrderTasks> seotBySeroId = servOrderTaskService.findSeotBySeroId(serviceOrderRespDto.getSeroId());
            List<SoTasksDto> soTasksDtos = TransactionMapper.mapEntityListToDtoList(seotBySeroId, SoTasksDto.class);
            List<SoTasksDto> serviceOrderTaskDtoList = new ArrayList<>();

            for (SoTasksDto soTasksDto : soTasksDtos) {
                SoTasksDto serviceOrderTaskDto = new SoTasksDto();
                serviceOrderTaskDto.setSeotId(soTasksDto.getSeotId());
                serviceOrderTaskDto.setSeotName(soTasksDto.getSeotName());
                serviceOrderTaskDto.setSeotStartDate(soTasksDto.getSeotStartDate());
                serviceOrderTaskDto.setSeotEndDate(soTasksDto.getSeotEndDate());
                serviceOrderTaskDto.setSeotStatus(soTasksDto.getSeotStatus());

                List<ServiceOrderWorkorder> sowoBySeotId = servOrderWorkorderService.findSowoBySeotId(soTasksDto.getSeotId());
                List<SoWorkorderDto> soWorkorderDtos = TransactionMapper.mapEntityListToDtoList(sowoBySeotId, SoWorkorderDto.class);
                List<SoWorkorderDto> serviceWorkorderDtoList = new ArrayList<>();

                for (SoWorkorderDto soWorkorderDto : soWorkorderDtos) {
                    SoWorkorderDto workorderDto = new SoWorkorderDto();
                    workorderDto.setSowoId(soWorkorderDto.getSowoId());
                    workorderDto.setSowoName(soWorkorderDto.getSowoName());
                    workorderDto.setSowoStatus(soWorkorderDto.getSowoStatus());
                    serviceWorkorderDtoList.add(workorderDto);
                }

                serviceOrderTaskDto.setServiceOrderWorkorders(soWorkorderDtos);

                serviceOrderTaskDtoList.add(serviceOrderTaskDto);
            }

            serviceOrderRespDto.setSoTasksDtoList(serviceOrderTaskDtoList);
        }

        ServicePremi servicePremi = servPremiService.findByServId(services.getServId());
        SemiDto semiDto = TransactionMapper.mapEntityToDto(servicePremi, SemiDto.class);

        List<ServicePremiCredit> servicePremiCredits = servPremiCreditService.findByServId(services.getServId());
        List<SecrDto> secrDtoList = TransactionMapper.mapEntityListToDtoList(servicePremiCredits, SecrDto.class);
        semiDto.setSecrDtoList(secrDtoList);

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);
  //      serviceRespDto.setCustomerResponseDTO(customerRequestById);
        serviceRespDto.setServiceOrdersList(serviceOrderRespDtos);
        serviceRespDto.setSemiDto(semiDto);

        return serviceRespDto;
    }

//    private Services generateFeasiblityType(CustomerRequest cr){
//        return Services.builder()
//                .servType(cr.getCreqType())
//                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
//                .servCreatedOn(cr.getCreqCreateDate())
//                .servStartDate(LocalDateTime.now())
//                .servEndDate(LocalDateTime.now().plusDays(7))
//                .servStatus(EnumModuleServiceOrders.ServStatus.ACTIVE)
//                .users(cr.getCustomer())
//                .customer(cr)
//                .build();
//    }

//    private Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus) {
//        Services existingService = soRepository.findById(cr.getServices().getServId())
//                .orElseThrow(() -> new EntityNotFoundException("ID is not found"));
//
//        existingService = Services.builder()
//                .servId(existingService.getServId())
//                .servType(cr.getCreqType())
//                .servCreatedOn(cr.getCreqCreateDate())
//                .servInsuranceNo(soAdapter.generatePolis(cr))
//                .servVehicleNumber(cr.getCustomerInscAssets().getCiasPoliceNumber())
//                .servStartDate(LocalDateTime.now())
//                .servEndDate(endDate)
//                .servStatus(servStatus)
//                .users(cr.getCustomer())
//                .customer(cr)
//                .build();
//
//        switch (existingService.getServType()) {
//            case POLIS -> generateServPremi(existingService, cr);
//            case CLOSE -> servPremiService.updateSemiStatus(
//                    EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), existingService.getServId());
//        }
//
//        return existingService;
//    }

//    private void generateServPremi(Services services, CustomerRequest cr){
//        ServicePremi servicePremi = ServicePremi.builder()
//                .semiServId(services.getServId())
//                .semiPremiDebet(cr.getCustomerInscAssets().getCiasTotalPremi())
//                .semiPaidType(cr.getCustomerInscAssets().getCiasPaidType().toString())
//                .semiStatus(EnumModuleServiceOrders.SemiStatus.UNPAID.toString()).build();
//
//        servPremiService.addSemi(servicePremi, services.getServId());
//    }

}
