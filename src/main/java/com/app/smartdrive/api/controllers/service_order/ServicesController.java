package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.dto.SoDto;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicesController {

    private final SoOrderService soOrderService;
    private final SoTasksService soTasksService;
    @GetMapping("/search")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){
        Optional<ServiceOrders> soById = soOrderService.findBySeroId(seroId);
        return new ResponseEntity<>(soById, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addServiceOrderById(@Valid @RequestBody ServiceOrders serviceOrders){

        Services services = new Services();
        Employees employees = new Employees();
        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String seroIdPolis = "PL-"+String.format("%04d", serviceOrders.getSeroId())+services.getServCreatedOn().format(formatter);
        String seroIdClaim = "CL-"+String.format("%04d", serviceOrders.getSeroId())+services.getServCreatedOn().format(formatter);
        String seroIdTutup = "TP-"+String.format("%04d", serviceOrders.getSeroId())+services.getServCreatedOn().format(formatter);

        serviceOrders.setSeroId(seroIdPolis);
        serviceOrders.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE);
        serviceOrders.setSeroStatus(EnumModuleServiceOrders.SeroStatus.OPEN);
        serviceOrders.setSeroReason("Test");
        serviceOrders.setServClaimNo("oo");
        serviceOrders.setServClaimStartdate(services.getServStartDate());
        serviceOrders.setServClaimEnddate(services.getServEndDate());
        serviceOrders.setSeroServId(services.getServId());
        serviceOrders.setSeroSeroId(serviceOrders.getSeroId());
        serviceOrders.setSeroAgentEntityid(employees.getEmpEntityid());
        serviceOrders.setSeroArwgCode(areaWorkGroup.getArwgCode());

        soOrderService.addSero(serviceOrders);

        return new ResponseEntity<>(serviceOrders, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getSoTasks(){
        return new ResponseEntity<>(soTasksService.findAllSoTasks(), HttpStatus.OK);
    }
}
