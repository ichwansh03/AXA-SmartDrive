package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.services.partner.PartnerAreaWorkgroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner-area-workgroups")
@RequiredArgsConstructor
public class PartnerAreaWorkgroupController {

    private final PartnerAreaWorkgroupService pawoService;

    @PostMapping
    public ResponseEntity<PartnerAreaWorkgroupDto> addPartnerAreaWorkGroup(@RequestBody PartnerAreaWorkgroupRequest request){

        PartnerAreaWorkgroup pawo = pawoService.save(request);
        return ResponseEntity.status(201).body(pawo.convertToDto());

    }
}
