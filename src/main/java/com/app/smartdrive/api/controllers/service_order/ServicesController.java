package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicesController {

    private final SoService soService;
    private final SoOrderService soOrderService;
    private final SoTasksService soTasksService;

    private ServiceControllerAdapter adapter;

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getSearchById(@RequestParam("seroId") String seroId){

        adapter = ServiceControllerAdapter.builder()
                .soService(soService)
                .soOrderService(soOrderService)
                .soTasksService(soTasksService).build();

        return new ResponseEntity<>(adapter.generateServiceDto(seroId), HttpStatus.OK);
    }

    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){
        return new ResponseEntity<>(soOrderService.findDtoById(seroId), HttpStatus.OK);
    }

    @GetMapping("/servcreq")
    public ResponseEntity<?> getServiceByCreqId(@RequestParam("creqid") Long creqId){
        return new ResponseEntity<>(soService.findCreqById(creqId), HttpStatus.OK);
    }

    @GetMapping("/servall")
    public ResponseEntity<?> getServiceAll(){
        return new ResponseEntity<>(soService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/addserv")
    public ResponseEntity<?> addServices(){
        Services services = new Services();
        return new ResponseEntity<>(soService.addServices(services), HttpStatus.OK);
    }

}
