package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.services.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partners")
public class PartnerController {

    private final PartnerService partnerService;
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
        return ResponseEntity.ok(partnerService.getAll().stream().map(Partner::convertToDto).toList());
    }
}
