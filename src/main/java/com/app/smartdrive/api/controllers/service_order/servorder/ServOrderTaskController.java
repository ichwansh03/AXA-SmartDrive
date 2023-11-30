package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
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
@RequestMapping("/seot")
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskController {

    private ServOrderTaskService servOrderTaskService;

    @Transactional
    @GetMapping("/servordertask")
    public ResponseEntity<?> getServiceOrderTasksById(@RequestParam("seotid") Long seotId){
        ServiceOrderTasks serviceOrderTasks = servOrderTaskService.findSeotById(seotId);

        Stream<SoWorkorderDto> sowoStream = serviceOrderTasks.getServiceOrderWorkorders()
                .stream()
                .map(serviceOrderWorkorder -> SoWorkorderDto.builder()
                        .sowoName(serviceOrderWorkorder.getSowoName())
                        //.sowoStatus(serviceOrderWorkorder.getSowoStatus())
                        .sowoSeotId(seotId).build());

        SoTasksDto soTasksDto = SoTasksDto.builder()
                .seotId(serviceOrderTasks.getSeotId())
                .seotName(serviceOrderTasks.getSeotName())
                .seotStatus(EnumModuleServiceOrders.SeotStatus.valueOf(serviceOrderTasks.getSeotStatus()))
                .seotStartDate(serviceOrderTasks.getSeotStartDate())
                .seotEndDate(serviceOrderTasks.getSeotEndDate())
                .serviceOrderWorkorders(sowoStream).build();

        log.info("ServiceOrdersController::getServiceOrderTasksById successfully viewed");
        return new ResponseEntity<>(soTasksDto, HttpStatus.OK);
    }
}
