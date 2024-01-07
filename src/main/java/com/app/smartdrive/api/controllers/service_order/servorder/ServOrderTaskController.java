package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.SeotPartnerDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceTasksFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/task")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ServOrderTaskController {

    private final ServOrderTaskService servOrderTaskService;
    private final ServiceTasksFactory serviceTasksTransaction;

    @GetMapping("{seotId}")
    public ResponseEntity<?> getTaskById(@PathVariable("seotId") Long seotId) {
        SoTasksDto taskById = servOrderTaskService.findTaskById(seotId);
        return new ResponseEntity<>(taskById, HttpStatus.OK);
    }

    @PutMapping("/{seotId}")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateSeotStatus(@Valid @RequestBody SoTasksDto soTasksDto, @PathVariable("seotId") Long seotId) {
        int updated = serviceTasksTransaction.updateTasksStatus(soTasksDto.getSeotStatus(), seotId);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update/claim/{seotId}")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateSeotStatus(@Valid @RequestBody SeotPartnerDto soTasksDto, @PathVariable("seotId") Long seotId) {

        serviceTasksTransaction.updateSeotPartner(soTasksDto, seotId);

        serviceTasksTransaction.updateTasksStatus(soTasksDto.getSeotStatus(), seotId);

        return new ResponseEntity<>(soTasksDto, HttpStatus.OK);
    }
}
