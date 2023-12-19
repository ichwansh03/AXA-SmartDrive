package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.request.PartnerAreaWorkgroupRequest;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkGroupId;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.partner.PartnerAreaWorkgroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<PartnerAreaWorkgroupDto>> getAll(){
        List<PartnerAreaWorkgroup> partnerAreaWorkgroupList = pawoService.getAll();
        return ResponseEntity.status(200).body(TransactionMapper.mapEntityListToDtoList(partnerAreaWorkgroupList, PartnerAreaWorkgroupDto.class));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@Valid @RequestBody PartnerAreaWorkGroupId id){
        //pawoService.deleteById(1L);
        return ResponseEntity.status(204).build();
    }


}
