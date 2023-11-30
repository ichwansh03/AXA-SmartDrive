package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.partner.implementation.PartnerContactServiceImpl;
import com.app.smartdrive.api.services.partner.implementation.PartnerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerServiceImpl partnerService;
    private final PartnerContactServiceImpl partnerContactService;

    @PostMapping("/{id}/partner-contacts")
    public ResponseEntity<PartnerContactDto> addPartnerContact(@RequestBody PartnerContactRequest request, @PathVariable("id") Long id){
        request.setPartnerId(id);
        PartnerContact partnerContact = partnerContactService.createEntity(request);
        return ResponseEntity.status(201).body(partnerContact.convertToDto());
    }

    @PutMapping("/{id}/partner-contacts/{userId}")
    public ResponseEntity<PartnerContactDto> addPartnerContact(@RequestBody PartnerContactRequest request, @PathVariable("id") Long id, @PathVariable("userId") Long userId){
        request.setPartnerId(id);
        PartnerContact partnerContact = partnerContactService.edit(request, userId);
        return ResponseEntity.status(201).body(partnerContact.convertToDto());
    }

    @DeleteMapping("/{id}/partner-contacts/{userId}")
    public ResponseEntity<String> deletePartnerContact(@PathVariable("id") Long id, @PathVariable("userId") Long userId){
        PartnerContactEntityId partnerContactEntityId = new PartnerContactEntityId();
        partnerContactEntityId.setPartnerId(id);
        partnerContactEntityId.setUserId(userId);
        partnerContactService.deleteById(partnerContactEntityId);
        return ResponseEntity.status(201).body("DELETE SUCCESS");
    }

    @PostMapping
    public ResponseEntity<PartnerDto> createPartner(@RequestBody PartnerRequest request){
        Partner partner = partnerService.convertToEntity(request);
        partnerService.save(partner);
        return ResponseEntity.status(201).body(partner.convertToDto());
    }
    @PutMapping("/{id}")
    public ResponseEntity<PartnerDto> updatePartnerById(@PathVariable("id") Long id, @RequestBody PartnerRequest request){
        Partner savePartner = partnerService.getById(id);
        Partner updatePartner = partnerService.convertToEntity(request);
        savePartner.setPartName(updatePartner.getPartName());
        savePartner.setPartAddress(updatePartner.getPartAddress());
        savePartner.setCity(updatePartner.getCity());
        savePartner.setPartNpwp(updatePartner.getPartNpwp());
        return ResponseEntity.ok(partnerService.save(savePartner).convertToDto());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePartnerById(@PathVariable("id") Long id){
        partnerService.deleteById(id);
        return ResponseEntity.ok().body("Success");
    }
    @GetMapping("/search")
    public ResponseEntity<Page<PartnerDto>> searchByNameOrNpwp(@RequestParam String search, @RequestParam(defaultValue = "0") int page){
        Page<Partner> AllPartnerWithSearch = partnerService.searchByNameOrNpwp(search,page);
        Page<PartnerDto> AllPartnerDtoWithSearch = AllPartnerWithSearch.map(Partner::convertToDto);
        return ResponseEntity.ok(AllPartnerDtoWithSearch);
    }

    @GetMapping
    public ResponseEntity<List<PartnerDto>> getAll(){
        List<Partner> partners = partnerService.getAll();
        return ResponseEntity.ok(partners.stream().map(Partner::convertToDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDto> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(partnerService.getById(id).convertToDto());
    }
}
