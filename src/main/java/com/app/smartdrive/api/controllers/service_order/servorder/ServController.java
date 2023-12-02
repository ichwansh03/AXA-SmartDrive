package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServController {

    private final ServService servService;

    @GetMapping("/serv")
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId){

        Services servicesById = servService.findServicesById(servId).get();

        ServiceRespDto serviceDto = TransactionMapper.mapEntityToDto(servicesById, ServiceRespDto.class);

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
