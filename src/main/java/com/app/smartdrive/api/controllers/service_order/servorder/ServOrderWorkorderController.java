package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sowo")
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderController {

    private final ServOrderWorkorderService servOrderWorkorderService;

    @GetMapping()
    public ResponseEntity<?> getServiceOrderWorkorderById(@RequestParam("sowoid") Long sowoId) {
        List<ServiceOrderWorkorder> sowoBySeotId = servOrderWorkorderService.findSowoBySeotId(sowoId);

        log.info("ServiceOrdersController::getServiceOrderTasksById successfully viewed");
        return new ResponseEntity<>(sowoBySeotId, HttpStatus.OK);
    }

    @PutMapping("/update/{sowoId}")
    public ResponseEntity<?> updateSoWorkorderStatus(@Valid @RequestBody SoWorkorderDto soWorkorderDto, @PathVariable("sowoId") Long sowoId) {
        int sowoStatus = servOrderWorkorderService.updateSowoStatus(soWorkorderDto.getSowoStatus(), sowoId);

        return new ResponseEntity<>(sowoStatus, HttpStatus.OK);
    }
}
