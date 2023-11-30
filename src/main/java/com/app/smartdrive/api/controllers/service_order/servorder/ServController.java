package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServController {

    private final ServService servService;

    @Transactional
    @GetMapping("/serv")
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId){

        Services servicesById = servService.findServicesById(servId);

        Stream<ServiceOrderRespDto> serviceOrderRespDtos = servicesById.getServiceOrdersSet()
                .stream()
                .map(seroById -> ServiceOrderRespDto.builder()
                        .seroId(seroById.getSeroId())
                        .seroOrdtType(seroById.getSeroOrdtType())
                        .seroStatus(seroById.getSeroStatus())
                        .seroReason(seroById.getSeroReason())
                        .seroServId(seroById.getServices().getServId())
                        .seroAgentEntityid(seroById.getEmployees().getEmpEntityid())
                        .seroArwgCode(seroById.getAreaWorkGroup().getArwgCode()).build());


        ServiceRespDto serviceRespDto = ServiceRespDto.builder()
                .servId(servicesById.getServId())
                .servType(servicesById.getServType())
                .servVehicleNumber(servicesById.getServVehicleNumber())
                .servInsuranceNo(servicesById.getServInsuranceNo())
                .servStartDate(servicesById.getServStartDate())
                .servEndDate(servicesById.getServEndDate())
                .servCustEntityid(servicesById.getUsers().getUserEntityId())
                .servCreqEntityid(servicesById.getCustomer().getCreqEntityId())
                .serviceOrderRespDtoStream(serviceOrderRespDtos).build();

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

    @GetMapping("/addserv")
    public ResponseEntity<?> generateService(@RequestParam("creqId") Long creqId){
        Services services = servService.addService(creqId);

        ServiceRespDto serviceRespDto = ServiceRespDto.builder()
                .servId(services.getServId())
                .servType(services.getServType())
                .servVehicleNumber(services.getServVehicleNumber())
                .servCustEntityid(services.getUsers().getUserEntityId())
                .servCreqEntityid(services.getCustomer().getCreqEntityId())
                .servStartDate(services.getServStartDate())
                .servEndDate(services.getServEndDate()).build();

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }
}
