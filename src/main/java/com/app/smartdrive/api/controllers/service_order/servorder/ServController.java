package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId) {

        ServiceRespDto servicesById = servService.findServicesById(servId);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(servicesById, HttpStatus.OK);
    }

    @GetMapping("/addserv")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> generateService(@RequestParam("creqId") Long creqId) throws Exception {
        Services services = servService.addService(creqId);

        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(services, ServiceRespDto.class);

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

}
