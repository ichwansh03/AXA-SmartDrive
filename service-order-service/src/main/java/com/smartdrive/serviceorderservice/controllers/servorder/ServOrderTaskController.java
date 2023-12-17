package com.smartdrive.serviceorderservice.controllers.servorder;

import com.smartdrive.serviceorderservice.dto.request.SeotPartnerDto;
import com.smartdrive.serviceorderservice.dto.response.SoTasksDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderTaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> updateSeotStatus(@Valid @RequestBody SoTasksDto soTasksDto, @PathVariable("seotId") Long seotId) {
        int updated = servOrderTaskService.updateTasksStatus(soTasksDto.getSeotStatus(), seotId);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update/claim/{seotId}")
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
