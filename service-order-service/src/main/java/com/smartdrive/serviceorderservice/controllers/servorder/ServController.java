package com.smartdrive.serviceorderservice.controllers.servorder;

import com.smartdrive.serviceorderservice.dto.response.ServiceRespDto;
import com.smartdrive.serviceorderservice.entities.Services;
import com.smartdrive.serviceorderservice.mapper.TransactionMapper;
import com.smartdrive.serviceorderservice.services.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServController {

    private final ServService servService;

    @GetMapping
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId) {

        ServiceRespDto servicesById = servService.findServicesById(servId);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(servicesById, HttpStatus.OK);
    }

    @GetMapping("/addserv")
    public ResponseEntity<?> generateService(@RequestParam("creqId") Long creqId) throws Exception {
        Services services = servService.addService(creqId);

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

}
