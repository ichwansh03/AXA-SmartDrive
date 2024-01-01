package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.servorder.ServiceWorkorderFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderController {

    private final ServiceWorkorderFactory serviceWorkorderFactory;

    @PutMapping("/workorder/update/{sowoId}")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateSoWorkorderStatus(@Valid @RequestBody SoWorkorderDto soWorkorderDto, @PathVariable("sowoId") Long sowoId) {
        int sowoStatus = serviceWorkorderFactory.updateSowoStatus(soWorkorderDto.getSowoStatus(), sowoId);

        return new ResponseEntity<>(sowoStatus, HttpStatus.OK);
    }
}
