package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    private ServOrderService servOrderService;

    @Transactional
    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){

        ServiceOrders seroById = servOrderService.findServiceOrdersById(seroId);

        Stream<SoTasksDto> soTasksDtoStream = seroById.getServiceOrderTasks()
                .stream()
                .map(serviceOrderTasks -> SoTasksDto.builder()
                        .seotId(serviceOrderTasks.getSeotId())
                        .seotName(serviceOrderTasks.getSeotName())
                        .seotStatus(serviceOrderTasks.getSeotStatus())
                        .seotStartDate(serviceOrderTasks.getSeotStartDate())
                        .seotEndDate(serviceOrderTasks.getSeotEndDate()).build());

        ServiceOrderRespDto serviceOrderRespDto = ServiceOrderRespDto.builder()
                .seroId(seroId)
                .seroOrdtType(seroById.getSeroOrdtType())
                .seroStatus(seroById.getSeroStatus())
                .seroReason(seroById.getSeroReason())
                .seroServId(seroById.getServices().getServId())
                .seroAgentEntityid(seroById.getEmployees().getEmpEntityid())
                .seroArwgCode(seroById.getAreaWorkGroup().getArwgCode())
                .soTasksDtoStream(soTasksDtoStream).build();

        log.info("ServiceOrdersController::getServiceOrderById successfully viewed");
        return new ResponseEntity<>(serviceOrderRespDto, HttpStatus.OK);
    }
}
