package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sowo")
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderController {

    private ServOrderWorkorderService servOrderWorkorderService;

    @Transactional
    @GetMapping("/servorderworkorder")
    public ResponseEntity<?> getServiceOrderWorkorderById(@RequestParam("sowoid") Long sowoId){
        ServiceOrderWorkorder serviceOrderWorkorder = servOrderWorkorderService.findBySowoId(sowoId);

        SoWorkorderDto soWorkorderDto = SoWorkorderDto.builder()
                .sowoName(serviceOrderWorkorder.getSowoName())
                //.sowoStatus(serviceOrderWorkorder.getSowoStatus())
                .sowoSeotId(serviceOrderWorkorder.getServiceOrderTasks().getSeotId()).build();

        log.info("ServiceOrdersController::getServiceOrderTasksById successfully viewed");
        return new ResponseEntity<>(soWorkorderDto, HttpStatus.OK);
    }
}
