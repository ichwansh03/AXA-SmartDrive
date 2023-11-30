package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.dto.service_order.request.ServiceOrderReqDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.ServOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServiceOrdersController {

    private final ServOrderService servOrderService;

    @Transactional
    @GetMapping("/serv")
    public ResponseEntity<?> getServiceById(@RequestParam("id") Long servId){

        Services servicesById = servOrderService.findServicesById(servId);

        ServiceRespDto serviceRespDto = ServiceRespDto.builder()
                .servId(servicesById.getServId())
                .servType(servicesById.getServType())
                .servVehicleNumber(servicesById.getServVehicleNumber())
                .servInsuranceNo(servicesById.getServInsuranceNo())
                .servStartDate(servicesById.getServStartDate())
                .servEndDate(servicesById.getServEndDate())
                .servCustEntityid(servicesById.getUsers().getUserEntityId())
                .servCreqEntityid(servicesById.getCustomer().getCreqEntityId()).build();

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){

        ServiceOrders seroById = servOrderService.findServiceOrdersById(seroId);

        ServiceOrderRespDto serviceOrderRespDto = ServiceOrderRespDto.builder()
                .seroId(seroId)
                .seroOrdtType(seroById.getSeroOrdtType())
                .seroStatus(seroById.getSeroStatus())
                .seroReason(seroById.getSeroReason())
                .seroServId(seroById.getServices().getServId())
                .seroAgentEntityid(seroById.getEmployees().getEmpEntityid())
                .seroArwgCode(seroById.getAreaWorkGroup().getArwgCode()).build();

        log.info("ServiceOrdersController::getServiceOrderById successfully viewed");
        return new ResponseEntity<>(serviceOrderRespDto, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/servordertask")
    public ResponseEntity<?> getServiceOrderTasksById(@RequestParam("seotid") Long seotId){
        ServiceOrderTasks serviceOrderTasks = servOrderService.findSeotById(seotId);

        SoTasksDto soTasksDto = SoTasksDto.builder()
                .seotId(serviceOrderTasks.getSeotId())
                .seotName(serviceOrderTasks.getSeotName())
                .seotStatus(serviceOrderTasks.getSeotStatus())
                .seotStartDate(serviceOrderTasks.getSeotStartDate())
                .seotEndDate(serviceOrderTasks.getSeotEndDate()).build();

        log.info("ServiceOrdersController::getServiceOrderTasksById successfully viewed");
        return new ResponseEntity<>(soTasksDto, HttpStatus.OK);
    }

    @PostMapping("/addsero")
    public ResponseEntity<?> generateSero(@Valid @RequestBody ServiceOrderReqDto serviceOrderReqDto){
        ServiceOrders serviceOrders = new ServiceOrders();

        //TransactionMapper.mapDtoToEntity(serviceOrderReqDto, serviceOrders);

        log.info("ServiceOrdersController::generateSero() successfully generated");

        return new ResponseEntity<>(servOrderService.addServiceOrders(serviceOrders), HttpStatus.OK);
    }

    @PostMapping("/addseot")
    public ResponseEntity<?> generateSeot(@Valid @RequestBody SoTasksDto soTasksDto){
        ServiceOrderTasks serviceOrderTasks = new ServiceOrderTasks();

        TransactionMapper.mapDtoToEntity(soTasksDto, serviceOrderTasks);

        log.info("ServiceOrdersController::generateSeot() successfully generated");

        return new ResponseEntity<>(servOrderService.addServiceOrderTasks(serviceOrderTasks), HttpStatus.OK);
    }

    @PostMapping("/addsowo")
    public ResponseEntity<?> generateSowo(@Valid @RequestBody SoWorkorderDto soWorkorderDto){
        ServiceOrderWorkorder serviceOrderWorkorder = new ServiceOrderWorkorder();

        TransactionMapper.mapDtoToEntity(soWorkorderDto, serviceOrderWorkorder);

        log.info("ServiceOrdersController::generateSeot() successfully generated");

        return new ResponseEntity<>(servOrderService.addSoWorkorder(serviceOrderWorkorder), HttpStatus.OK);
    }
}
