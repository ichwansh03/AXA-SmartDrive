package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import com.app.smartdrive.api.services.service_order.implementation.SoAdapter;
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

    private final SoService soService;
    private final SoOrderService soOrderService;
    private final SoTasksService soTasksService;

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchById(@RequestParam("seroId") Long seroId){

        SoAdapter adapter = SoAdapter.builder()
                .soService(soService)
                .soOrderService(soOrderService)
                .soTasksService(soTasksService).build();
        log.info("ServiceOrdersController::getSearchById successfully viewed");
        return new ResponseEntity<>(adapter.generateServiceDto(seroId), HttpStatus.OK);
    }

    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") Long seroId){
        log.info("ServiceOrdersController::getServiceOrderById successfully viewed");
        return new ResponseEntity<>(soOrderService.findDtoById(seroId), HttpStatus.OK);
    }

    @PostMapping("/addsero")
    public ResponseEntity<?> generateSero(@RequestParam("creqType") EnumCustomer.CreqType creqType,
                                          @RequestParam("arwgCode") String arwgCode){
        log.info("ServiceOrdersController::generateSero() successfully generated");
        return new ResponseEntity<>(soOrderService.addServiceOrders(creqType, arwgCode), HttpStatus.OK);
    }
}
