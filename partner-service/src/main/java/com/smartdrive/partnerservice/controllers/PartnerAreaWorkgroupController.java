package com.smartdrive.partnerservice.controllers;

import com.smartdrive.partnerservice.dto.PartnerAreaWorkgroupDto;
import com.smartdrive.partnerservice.dto.request.PartnerAreaWorkgroupRequest;
import com.smartdrive.partnerservice.entities.PartnerAreaWorkgroup;
import com.smartdrive.partnerservice.mapper.TransactionMapper;
import com.smartdrive.partnerservice.services.PartnerAreaWorkgroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner-area-workgroups")
@RequiredArgsConstructor
public class PartnerAreaWorkgroupController {

    private final PartnerAreaWorkgroupService pawoService;

    @PostMapping
    public ResponseEntity<PartnerAreaWorkgroupDto> addPartnerAreaWorkGroup(@Valid @RequestBody PartnerAreaWorkgroupRequest request){

        PartnerAreaWorkgroup pawo = pawoService.create(request);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(pawo, PartnerAreaWorkgroupDto.class));

    }
}
