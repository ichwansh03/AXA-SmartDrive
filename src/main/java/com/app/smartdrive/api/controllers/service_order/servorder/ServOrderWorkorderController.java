package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceWorkorderFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/workorder")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class ServOrderWorkorderController {

    private final ServiceWorkorderFactory serviceWorkorderFactory;
    private final ServOrderWorkorderService servOrderWorkorderService;

    @GetMapping
    public ResponseEntity<?> getWorkorderByTaskId(@RequestParam("seotId") Long seotId){
        return new ResponseEntity<>(servOrderWorkorderService.findSowoBySeotId(seotId), HttpStatus.OK);
    }

    @PutMapping("/update/{sowoId}")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateSoWorkorderStatus(@Valid @RequestBody SoWorkorderDto soWorkorderDto, @PathVariable("sowoId") Long sowoId) {
        int sowoStatus = serviceWorkorderFactory.updateSowoStatus(soWorkorderDto.getSowoStatus(), sowoId);

        return new ResponseEntity<>(sowoStatus, HttpStatus.OK);
    }
}
