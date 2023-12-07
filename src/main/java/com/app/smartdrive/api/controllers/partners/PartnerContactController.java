package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.controllers.users.UserController;
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
    private final UserController userController;


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
        userController.deleteUser(userId);
        return ResponseEntity.status(204).build();
    }
}
