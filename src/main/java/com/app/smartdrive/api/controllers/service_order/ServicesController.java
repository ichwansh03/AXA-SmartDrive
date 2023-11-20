package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.service_orders.ServiceOrders;
import com.app.smartdrive.api.services.service_orders.SoOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicesController {

    private final SoOrderService service;

    @GetMapping("/search")
    public ResponseEntity<?> getServicesById(@RequestParam("seroid") String seroId){
        Optional<ServiceOrders> soById = service.findBySeroId(seroId);
        return new ResponseEntity<>(soById, HttpStatus.OK);
    }

}
