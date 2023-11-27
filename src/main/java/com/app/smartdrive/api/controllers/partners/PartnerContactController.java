package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.services.partner.implementation.PartnerContactServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partnerContacts")
@RequiredArgsConstructor
public class PartnerContactController {

    private final PartnerContactServiceImpl partnerContactService;

    public ResponseEntity<PartnerContactDto> savePartnerContact(@RequestBody PartnerContactRequest request){

        return null;

    }
}
