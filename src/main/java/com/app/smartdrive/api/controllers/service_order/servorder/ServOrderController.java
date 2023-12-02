package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServSeroDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/sero")
@RequiredArgsConstructor
@Slf4j
public class ServOrderController {

    private final ServOrderService servOrderService;

    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){

        ServiceOrders seroById = servOrderService.findServiceOrdersById(seroId);

        Stream<SoTasksDto> soTasksDtoStream = seroById.getServiceOrderTasks()
                .stream()
                .map(serviceOrderTasks -> SoTasksDto.builder()
                        .seotId(serviceOrderTasks.getSeotId())
                        .seotName(serviceOrderTasks.getSeotName())
                        .seotStatus(EnumModuleServiceOrders.SeotStatus.INPROGRESS.toString())
                        .seotStartDate(serviceOrderTasks.getSeotStartDate())
                        .seotEndDate(serviceOrderTasks.getSeotEndDate()).build());

        ServiceOrderRespDto serviceOrderRespDto = ServiceOrderRespDto.builder()
                .seroId(seroId)
                .seroOrdtType(seroById.getSeroOrdtType())
                .seroStatus(seroById.getSeroStatus())
                .seroReason(seroById.getSeroReason())
                .soTasksDtoStream(soTasksDtoStream).build();

        log.info("ServiceOrdersController::getServiceOrderById successfully viewed");
        return new ResponseEntity<>(serviceOrderRespDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllBySeroId(@RequestParam("seroId") String seroId){
        ServiceOrders serviceOrders = servOrderService.findServiceOrdersById(seroId);

        ServSeroDto servSeroDto = ServSeroDto.builder()
                .seroId(seroId)
                .servCreatedOn(serviceOrders.getServices().getServCreatedOn())
                .seroStatus(serviceOrders.getSeroStatus())
                .userName(serviceOrders.getServices().getUsers().getUserName())
                .empName(serviceOrders.getEmployees().getEmpName()).build();

        return new ResponseEntity<>(servSeroDto, HttpStatus.OK);
    }
}
