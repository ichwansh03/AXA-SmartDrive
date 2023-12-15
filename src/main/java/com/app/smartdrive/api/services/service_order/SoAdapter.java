package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class SoAdapter {

    private ServService servService;
    private ServOrderService servOrderService;
    private ServOrderTaskService servOrderTaskService;
    private ServOrderWorkorderService servOrderWorkorderService;

    private ServPremiService servPremiService;
    private ServPremiCreditService servPremiCreditService;

    private UserService userService;
    private CustomerRequestService customerRequestService;
    private EmployeeAreaWorkgroupService employeeAreaWorkgroupService;

    private int seroSequence = 0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public String formatServiceOrderId(Services services){

        String servTypes = services.getServType().toString();

        log.info("Format ID for ServiceOrders has been created");

        String formatSeroId = String.format("%04d", getNextSequenceNumber());
        String formatEndDate = services.getServCreatedOn().format(formatter);

        String formatId;

        switch (servTypes) {
            case "POLIS" -> formatId = "PL" + formatSeroId + "-" + formatEndDate;
            case "CLAIM" -> formatId = "CL" + formatSeroId + "-" + formatEndDate;
            case "FEASIBLITY" -> formatId = "FS" + formatSeroId + "-" + formatEndDate;
            default -> formatId = "TP" + formatSeroId + "-" + formatEndDate;
        }

        return formatId;
    }

    private synchronized int getNextSequenceNumber() {
        // Increment sequence number and return the updated value
        return ++seroSequence;
    }

    public String generatePolis(CustomerRequest cr){
        String servTypes = cr.getCreqType().toString();
        String createdDate = cr.getCreqCreateDate().format(formatter);
        String formatPolisId = String.format("%03d", cr.getCustomer().getUserEntityId());

        return switch (servTypes) {
            case "POLIS", "CLAIM" -> formatPolisId+"-"+createdDate;
            default -> "-";
        };

    }

    public ServiceRespDto responseServices(Services services) {
        List<ServiceOrderRespDto> serviceOrderRespDtoList = mapServiceOrdersToDtoList(services.getServId());
        SemiDto semiDto = mapServPremiToDto(services.getServId());

        ServiceRespDto serviceDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);
        serviceDto.setServiceOrdersList(serviceOrderRespDtoList);
        serviceDto.setSemiDto(semiDto);

        return serviceDto;
    }

    private UserDto mapUserToDto(Long userEntityId) {
        User userById = userService.getUserById(userEntityId).orElseThrow(EntityNotFoundException::new);
        return TransactionMapper.mapEntityToDto(userById, UserDto.class);
    }

    private List<ServiceOrderRespDto> mapServiceOrdersToDtoList(Long servId) {
        List<ServiceOrders> serviceOrders = servOrderService.findAllSeroByServId(servId);
        return serviceOrders.stream()
                .map(this::responseServiceOrders)
                .collect(Collectors.toList());
    }

    private ServiceOrderRespDto responseServiceOrders(ServiceOrders serviceOrders) {
        Services servicesById = servService.findServicesById(serviceOrders.getServices().getServId());
        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(servicesById, ServiceRespDto.class);

        List<ServiceOrderTasks> serviceOrderTasks = servOrderTaskService.findSeotBySeroId(serviceOrders.getSeroId());
        List<SoTasksDto> soTasksDtos = TransactionMapper.mapEntityListToDtoList(serviceOrderTasks, SoTasksDto.class);

        ServiceOrderRespDto serviceOrderRespDto = TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class);
        serviceOrderRespDto.setServices(serviceRespDto);
        serviceOrderRespDto.setSoTasksDtoList(soTasksDtos);

        return serviceOrderRespDto;
    }

    private SemiDto mapServPremiToDto(Long servId) {
        ServicePremi servicePremis = servPremiService.findByServId(servId);

        List<ServicePremiCredit> servicePremiCredits = servPremiCreditService.findByServId(servicePremis.getSemiServId());
        List<SecrDto> secrDtoList = TransactionMapper.mapEntityListToDtoList(servicePremiCredits, SecrDto.class);

        SemiDto semiDto = TransactionMapper.mapEntityToDto(servicePremis, SemiDto.class);
        semiDto.setSecrDtoList(secrDtoList);

        return semiDto;
    }
}
