package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.customer.request.ClaimRequestDTO;
import com.app.smartdrive.api.dto.service_order.response.*;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ServController {

    private final ServService servService;
    private final ServiceFactory serviceFactory;

    @GetMapping
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> getServiceById(@RequestParam("servid") Long servId) {

        ServiceRespDto servicesById = servService.findServicesById(servId);

        log.info("ServiceOrdersController::getServiceById successfully viewed");
        return new ResponseEntity<>(servicesById, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> generateService(@Valid @RequestBody ClaimRequestDTO requestDTO) throws Exception {

        ServiceDto serviceRespDto = serviceFactory.addService(requestDTO.getCreqEntityId());

        log.info("ServiceOrdersController::generateService successfully viewed");
        return new ResponseEntity<>(serviceRespDto, HttpStatus.OK);
    }

}
