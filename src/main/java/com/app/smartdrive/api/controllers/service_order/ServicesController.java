package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicesController {

    private final SoService soService;
    private final SoOrderService soOrderService;
    private final SoTasksService soTasksService;

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchById(@RequestParam("seroId") String seroId){

        ServiceControllerAdapter adapter = ServiceControllerAdapter.builder()
                .soService(soService)
                .soOrderService(soOrderService)
                .soTasksService(soTasksService).build();

        return new ResponseEntity<>(adapter.generateServiceDto(seroId), HttpStatus.OK);
    }

    @GetMapping("/serv")
    public ResponseEntity<?> generateServices(@RequestParam("servId") Long servId){

        return new ResponseEntity<>(soService.getById(servId), HttpStatus.OK);
    }

    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){
        return new ResponseEntity<>(soOrderService.findDtoById(seroId), HttpStatus.OK);
    }

    private String formatServiceOrderId(String servType, Long seroId, LocalDate createdAt){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatSeroId = String.format("%04d", seroId);

        if (servType.equals("POLIS")){
            return "PL"+formatSeroId+"-"+createdAt.format(formatter);
        } else if (servType.equals("CLAIM")) {
            return "CL"+formatSeroId+"-"+createdAt.format(formatter);
        }
        return "TP"+formatSeroId+"-"+createdAt.format(formatter);
    }

}
