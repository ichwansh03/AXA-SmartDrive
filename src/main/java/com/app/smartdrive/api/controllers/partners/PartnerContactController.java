package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.partner.PartnerContactService;
import com.app.smartdrive.api.services.partner.implementation.PartnerContactServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner-contacts")
@RequiredArgsConstructor
public class PartnerContactController {

    private final PartnerContactService partnerContactService;

    @PostMapping
    public ResponseEntity<PartnerContactDto> addPartnerContact(@Valid @RequestBody PartnerContactRequest request){
        PartnerContact partnerContact = partnerContactService.create(request);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(partnerContact, PartnerContactDto.class));
    }

    @PutMapping("/{id}/partner-contacts/{userId}")
    public ResponseEntity<PartnerContactDto> addPartnerContact(@RequestBody PartnerContactRequest request, @PathVariable("id") Long id, @PathVariable("userId") Long userId){
        request.setPartnerId(id);
        PartnerContact partnerContact = partnerContactService.edit(request,userId);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(partnerContact, PartnerContactDto.class));
    }

    @DeleteMapping("/{id}/partner-contacts/{userId}")
    public ResponseEntity<String> deletePartnerContact(@PathVariable("id") Long id, @PathVariable("userId") Long userId){
        PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId();
        partnerContactEntityId.setPartnerId(id);
        partnerContactEntityId.setUserId(userId);
        partnerContactService.deleteById(partnerContactEntityId);
        return ResponseEntity.status(201).body("DELETE SUCCESS");
    }
}
