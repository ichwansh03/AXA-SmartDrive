package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    private final CustomerRequestService customerRequestService;
    private final ServiceFactory serviceFactory;

    @Transactional(readOnly = true)
    @Override
    public ServiceRespDto findServicesById(Long servId) {
        Services services = soRepository.findById(servId)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + servId + " not found"));
        log.info("SoOrderServiceImpl::findServicesById in ID {} ",services.getServId());

        CustomerRequest existCustomerRequest = customerRequestService.getById(services.getCustomer().getCreqEntityId());
        CustomerResponseDTO customerRequestById = TransactionMapper.mapEntityToDto(existCustomerRequest, CustomerResponseDTO.class);
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

}
