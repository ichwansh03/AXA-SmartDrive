package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.SeotPartnerDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seot")
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskController {

    private final ServOrderTaskService servOrderTaskService;

    @GetMapping()
    public ResponseEntity<?> getServiceOrderTasksById(@RequestParam("seroId") String seroId) {
        List<ServiceOrderTasks> seotBySeroId = servOrderTaskService.findSeotBySeroId(seroId);
        log.info("ServiceOrdersController::getServiceOrderTasksById successfully viewed");
        return new ResponseEntity<>(seotBySeroId, HttpStatus.OK);
    }

    @PutMapping("/update/{seotId}")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateSeotStatus(@Valid @RequestBody SoTasksDto soTasksDto, @PathVariable("seotId") Long seotId) {
        int updated = servOrderTaskService.updateTasksStatus(soTasksDto.getSeotStatus(), seotId);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update/claim/{seotId}")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateSeotStatus(@Valid @RequestBody SeotPartnerDto soTasksDto, @PathVariable("seotId") Long seotId) {

        SeotPartnerDto seotPartnerDto = new SeotPartnerDto();
        seotPartnerDto.setPartnerId(soTasksDto.getPartnerId());
        seotPartnerDto.setRepair(soTasksDto.getRepair());
        seotPartnerDto.setSparepart(soTasksDto.getSparepart());
        servOrderTaskService.updateSeotPartner(seotPartnerDto, seotId);

        servOrderTaskService.updateTasksStatus(soTasksDto.getSeotStatus(), seotId);

        return new ResponseEntity<>(seotPartnerDto, HttpStatus.OK);
    }
}
