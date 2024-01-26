package com.app.smartdrive.api.services.service_order.servorder.services.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.services.ServService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServImpl implements ServService {

    private final SoRepository soRepository;
    private final ServOrderService servOrderService;

    private final ServPremiService servPremiService;
    private final CustomerRequestService customerRequestService;
    private final UserService userService;

    @Override
    public ServiceRespDto getById(Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + servId + " not found"));
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",services.getServId());
        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);

        customerRequestService.mapCustomerRequestToDtoServices(services, serviceRespDto);
        userService.mapUserToDtoServices(services, serviceRespDto);
        servOrderService.mapServiceOrderToDtoServices(services, serviceRespDto);
        servPremiService.mapServicePremiToDtoServices(services, serviceRespDto);

        return serviceRespDto;
    }

    @Override
    public List<ServiceRespDto> getAll() {
        List<Services> services = soRepository.findAll();
        return TransactionMapper.mapEntityListToDtoList(services, ServiceRespDto.class).stream()
                .map(serviceRespDto -> getById(serviceRespDto.getServId()))
                .toList();
    }

    @Override
    public List<FeasiblityDto> findAllFeasiblity() {

        List<ServiceRespDto> serviceRespDtoList = getAll();

        return serviceRespDtoList.stream()
                .filter(type -> type.getServType().equals(EnumCustomer.CreqType.FEASIBLITY))
                .map(feasiblity -> FeasiblityDto.builder()
                        .creqId(feasiblity.getCustomerResponseDTO().getCreqEntityId())
                        .servType(feasiblity.getServType())
                        .createdOn(feasiblity.getServCreatedOn())
                        .customerName(feasiblity.getUserDto().getUserFullName())
                        .customerEmail(feasiblity.getUserDto().getUserEmail())
                        .seroId(feasiblity.getServiceOrdersList().get(0).getSeroId())
                        .servStatus(feasiblity.getServStatus()).build())
                .toList();
    }

    @Override
    public List<PolisDto> findAllPolis() {
        List<ServiceRespDto> serviceRespDtoList = getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return serviceRespDtoList.stream()
                .filter(type -> !type.getServType().equals(EnumCustomer.CreqType.FEASIBLITY))
                .map(feasiblity -> PolisDto.builder()
                        .creqId(feasiblity.getCustomerResponseDTO().getCreqEntityId())
                        .seroId(feasiblity.getServiceOrdersList().get(0).getSeroId())
                        .customerName(feasiblity.getUserDto().getUserFullName())
                        .polisNo(feasiblity.getServInsuranceno())
                        .periode(feasiblity.getServEnddate().format(formatter))
                        .vehicleNo(feasiblity.getServVehicleno())
                        .servType(feasiblity.getServType())
                        .totalPremi(feasiblity.getCustomerResponseDTO().getCustomerInscAssets().getCiasTotalPremi()).build())
                .toList();
    }

}
