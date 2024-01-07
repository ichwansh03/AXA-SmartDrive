package com.app.smartdrive.api.controllers.partners;

import com.app.smartdrive.api.dto.partner.PartnerAreaWorkgroupDto;
import com.app.smartdrive.api.dto.partner.PartnerContactDto;
import com.app.smartdrive.api.dto.partner.PartnerDto;
import com.app.smartdrive.api.dto.partner.request.PartnerContactRequest;
import com.app.smartdrive.api.dto.partner.request.PartnerRequest;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.partner.PartnerAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.PartnerContact;
import com.app.smartdrive.api.entities.partner.PartnerContactEntityId;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.services.partner.PartnerService;
import com.app.smartdrive.api.services.partner.implementation.PartnerContactServiceImpl;
import com.app.smartdrive.api.services.partner.implementation.PartnerServiceImpl;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partners")
@CrossOrigin
public class PartnerController {

    private final PartnerServiceImpl partnerService;
    private final ServOrderWorkorderService servOrderWorkorderService;

    @PostMapping
    public ResponseEntity<PartnerDto> createPartner(@Valid @RequestBody PartnerRequest request){
        Partner partner = partnerService.save(request);
        return ResponseEntity.status(201).body(TransactionMapper.mapEntityToDto(partner, PartnerDto.class));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PartnerDto> updatePartnerById(@PathVariable("id") Long id, @RequestBody PartnerRequest request){
        Partner updatePartner = partnerService.update(request);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(updatePartner, PartnerDto.class));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePartnerById(@PathVariable("id") Long id){
        partnerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/search")
    public ResponseEntity<Page<PartnerDto>> searchByNameOrNpwp(@RequestParam String search, @RequestParam(defaultValue = "0") int page){
        Page<Partner> AllPartnerWithSearch = partnerService.searchByNameOrNpwp(search,page);
        Page<PartnerDto> AllPartnerDtoWithSearch = AllPartnerWithSearch.map(partner -> TransactionMapper.mapEntityToDto(partner, PartnerDto.class));
        return ResponseEntity.ok(AllPartnerDtoWithSearch);
    }

    @GetMapping
    public ResponseEntity<Page<PartnerDto>> getAll(@RequestParam(defaultValue = "0") int page){
        Page<Partner> partners = partnerService.getAll(page);
        Page<PartnerDto> partnersDto = partners.map(partner -> TransactionMapper.mapEntityToDto(partner, PartnerDto.class));
        return ResponseEntity.ok(partnersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerDto> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(partnerService.getById(id), PartnerDto.class));
    }
    @GetMapping("/workorder")
    public ResponseEntity<?> getAllServiceWorkOrder(@RequestParam Long entityid, @RequestParam(required = false) String arwgcode){
        List<ServiceOrderWorkorder> serviceOrderWorkorderList = servOrderWorkorderService.findAllByPartnerAndArwgCode(entityid,arwgcode);
        return ResponseEntity.status(200).body(serviceOrderWorkorderList);
    }
}
