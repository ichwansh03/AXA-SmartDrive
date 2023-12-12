package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.app.smartdrive.api.services.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServController {

    private final ServService servService;
    private final ServOrderService servOrderService;
    private final UserService userService;
    private final CustomerRequestService customerRequestService;

    @GetMapping
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId) {

        Services servicesById = servService.findServicesById(servId).get();

        ServiceRespDto serviceRespDto = responseService(servicesById);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

    @GetMapping("/addserv")
    public ResponseEntity<?> generateService(@RequestParam("creqId") Long creqId) throws Exception {
        Services services = servService.addService(creqId);

        ServiceRespDto serviceRespDto = responseService(services);

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

    private ServiceRespDto responseService(Services services) {

        User userById = userService.getUserById(services.getUsers().getUserEntityId()).get();
        UserDto userDto = TransactionMapper.mapEntityToDto(userById, UserDto.class);

        CustomerResponseDTO creqDto = customerRequestService.getCustomerRequestById(services.getCustomer().getCreqEntityId());

        List<ServiceOrders> serviceOrders = servOrderService.findAllSeroByServId(services.getServId());
        List<ServiceOrderRespDto> serviceOrderRespDtoClass = TransactionMapper.mapListDtoToListEntity(serviceOrders, ServiceOrderRespDto.class);

        ServiceRespDto serviceDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);
        serviceDto.setUserDto(userDto);
        serviceDto.setCustomerResponseDTO(creqDto);
        serviceDto.setServiceOrdersList(serviceOrderRespDtoClass);

        return serviceDto;
    }
}
