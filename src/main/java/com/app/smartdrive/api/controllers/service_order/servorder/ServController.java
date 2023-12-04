package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId){

        Services servicesById = servService.findServicesById(servId).get();
        User userById = userService.getUserById(servicesById.getUsers().getUserEntityId()).get();
        List<ServiceOrders> serviceOrders = servOrderService.findAllSeroByServId(servId);
        List<ServiceOrderRespDto> serviceOrderRespDtoClass = TransactionMapper.mapListDtoToListEntity(serviceOrders, ServiceOrderRespDto.class);

        UserDto userDto = TransactionMapper.mapEntityToDto(userById, UserDto.class);
        CustomerResponseDTO creqDto = customerRequestService.getCustomerRequestById(servicesById.getCustomer().getCreqEntityId());

        ServiceRespDto serviceDto = TransactionMapper.mapEntityToDto(servicesById, ServiceRespDto.class);
        serviceDto.setUserDto(userDto);
        serviceDto.setCustomerResponseDTO(creqDto);
        serviceDto.setServiceOrdersList(serviceOrderRespDtoClass);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(serviceDto, HttpStatus.OK);
    }

    @GetMapping("/addserv")
    public ResponseEntity<?> generateService(@RequestParam("creqId") Long creqId){
        Services services = servService.addService(creqId);

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

}
