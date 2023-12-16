package com.smartdrive.partnerservice.controllers;

import com.smartdrive.partnerservice.dto.PartnerContactDto;
import com.smartdrive.partnerservice.dto.request.PartnerContactRequest;
import com.smartdrive.partnerservice.entities.PartnerContact;
import com.smartdrive.partnerservice.mapper.TransactionMapper;
import com.smartdrive.partnerservice.services.PartnerContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner-contacts")
@RequiredArgsConstructor
public class PartnerContactController {
    private final PartnerContactService partnerContactService;
//    private final UserController userController;


    @PostMapping
    public ResponseEntity<PartnerContactDto> addPartnerContact(@Valid @RequestBody PartnerContactRequest request){
        PartnerContact partnerContact = partnerContactService.create(request);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(partnerContact, PartnerContactDto.class));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<PartnerContactDto> addPartnerContact(@Valid @RequestBody PartnerContactRequest request, @PathVariable("userId") Long userId){
        PartnerContact partnerContact = partnerContactService.edit(request,userId);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(partnerContact, PartnerContactDto.class));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deletePartnerContact(@PathVariable("userId") Long userId){
//        userController.deleteUser(userId);
        return ResponseEntity.status(204).build();
    }
}
