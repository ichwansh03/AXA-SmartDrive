package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.servorder.*;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoRepository soRepository;
    private final SoOrderRepository soOrderRepository;
    private final ServOrderTaskService servOrderTaskService;

    private final CustomerRequestService customerRequestService;
    private final UserService userService;

    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findById(seroId)
                .orElseThrow(() -> new EntityNotFoundException("ID "+seroId+" is not found"));
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

    @Override
    public ServiceOrderRespDto findOrderDtoById(String seroId) {
        ServiceOrders orders = findServiceOrdersById(seroId);

        Services services = soRepository.findById(orders.getServices().getServId()).get();
        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);

        CustomerRequest existCustomerRequest = customerRequestService.getById(services.getCustomer().getCreqEntityId());
        CustomerResponseDTO customerRequestById = TransactionMapper.mapEntityToDto(existCustomerRequest, CustomerResponseDTO.class);

        User user = userService.getById(services.getUsers().getUserEntityId());
        UserDto userDto = TransactionMapper.mapEntityToDto(user, UserDto.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        serviceRespDto.setServCreatedOn(services.getServCreatedOn().format(formatter));

        serviceRespDto.setServCreatedOn(services.getServCreatedOn().format(formatter));
        serviceRespDto.setUserDto(userDto);
        serviceRespDto.setCustomerResponseDTO(customerRequestById);

        ServiceOrderRespDto serviceOrderRespDto = TransactionMapper.mapEntityToDto(orders, ServiceOrderRespDto.class);
        List<SoTasksDto> taskByOrderId = servOrderTaskService.findAllTaskByOrderId(seroId);

        serviceOrderRespDto.setServices(serviceRespDto);
        serviceOrderRespDto.setSoTasksDtoList(taskByOrderId);
        return serviceOrderRespDto;
    }

    @Override
    public List<ServiceOrderRespDto> findAllOrderByServId(Long servId) {
        List<ServiceOrders> ordersList = soOrderRepository.findByServices_ServId(servId);

        return ordersList.stream()
                .map(serviceOrders -> {
                    ServiceOrderRespDto dto = TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class);
                    List<SoTasksDto> soTasksDtoList = servOrderTaskService.findAllTaskByOrderId(dto.getSeroId());
                    dto.setSoTasksDtoList(soTasksDtoList);
                    return dto;
                })
                .toList();
    }

    @Override
    public List<ServiceOrders> findAllSeroByUserId(Long custId) {
        List<ServiceOrders> entityId = soOrderRepository.findByServices_Users_UserEntityId(custId);

        log.info("SoOrderServiceImpl::findAllSeroByServId from user ID {} ",custId);

        return entityId;
    }

    @Override
    public Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus) {

        EnumModuleServiceOrders.SeroStatus status = EnumModuleServiceOrders.SeroStatus.valueOf(seroStatus);
        Page<ServiceOrders> serviceOrdersPage;

        if (Objects.equals(seroOrdtType, "ALL")) {
            serviceOrdersPage = soOrderRepository.findAll(pageable);
        } else {
            EnumModuleServiceOrders.SeroOrdtType type = EnumModuleServiceOrders.SeroOrdtType.valueOf(seroOrdtType);
            serviceOrdersPage = soOrderRepository.findBySeroOrdtTypeAndSeroStatus(pageable, type, status);
        }

        return serviceOrdersPage.map(serviceOrders -> TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class));
    }

}
