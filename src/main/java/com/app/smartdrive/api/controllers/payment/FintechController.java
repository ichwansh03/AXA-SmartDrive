package com.app.smartdrive.api.controllers.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.BanksDto;
import com.app.smartdrive.api.dto.payment.FintechDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.services.payment.FintechService;
import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class FintechController {
    private final FintechService service;
    private final BusinessEntityImpl serviceBusiness;

    @GetMapping("/fintech/all")
    public ResponseEntity<?> getAllFintech(){
        List<Fintech> listFintech = service.findAllFintech();
        
        List<BanksDto> listDto = listFintech.stream().map(banks ->{
            BanksDto dto = new BanksDto();
            dto.setBank_name(banks.getFint_name());
            dto.setBank_desc(banks.getFint_desc());
            return dto;
        }).collect(Collectors.toList());
        
        
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }
    @GetMapping("/fintech/{fint_entityid}")
    public ResponseEntity<?> getFintechById(@Valid @PathVariable("fint_entityid") Long fint_entityid){
        Optional<Fintech> fintechData = service.getFintechById(fint_entityid);
        List<Fintech> fintechList = service.findAllFintech();
        FintechDto dto = new FintechDto();

        if(fintechData.isPresent()){
            for (Fintech fintech : fintechList) {
                if(fintech.getFint_entityid().equals(fint_entityid)){
                    dto.setFint_name(fintech.getFint_name());
                    dto.setFint_desc(fintech.getFint_desc());
                }
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id tidak ditemukan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
      
    }

    @PostMapping("/fintech/add")
    public ResponseEntity<?> addFintech(@Valid 
    @RequestParam(name = "fint_name", required = true) String fint_name,
    @RequestParam(name = "fint_desc", required = true) String fint_desc){
        BusinessEntity busines = new BusinessEntity();
        busines.setEntityModifiedDate(LocalDateTime.now());
        Long businessEntityId = serviceBusiness.save(busines);

        Fintech fintech = new Fintech();
        fintech.setBusinessEntity(busines);
        fintech.setFint_entityid(businessEntityId);
        fintech.setFint_name(fint_name);
        fintech.setFint_desc(fint_desc);
        service.addFintech(fintech);

        FintechDto dto = new FintechDto();
        dto.setFint_name(fintech.getFint_name());
        dto.setFint_desc(fintech.getFint_desc());

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/fintech/{fint_entityid}/update")
    public ResponseEntity<?> updateFintechById(@Valid @PathVariable("fint_entityid") Long fint_entityid,
    @RequestParam(name = "fint_name", required = true) String fint_name,
    @RequestParam(name = "fint_desc", required = true) String fint_desc){
        Optional<Fintech> fintechData = service.getFintechById(fint_entityid);
        Fintech fintech = fintechData.get();
        FintechDto fintechDto = new FintechDto();
        if(fintechData.isPresent()){
            fintech.setFint_name(fint_name);
            fintech.setFint_desc(fint_desc);
            service.addFintech(fintech);

            fintechDto.setFint_name(fintech.getFint_name());
            fintechDto.setFint_desc(fintech.getFint_desc());
            return new ResponseEntity<>(fintechDto, HttpStatus.OK);
        } 
        else{
            return new ResponseEntity<>("Id Not Found", HttpStatus.OK);
        }
    }

    @DeleteMapping("/fintech/delete/{fint_entityid}")
    public ResponseEntity<?> deleteFintechById(@Valid @PathVariable("fint_entityid") Long fint_entityid){
        Optional<Fintech> fintechData = service.getFintechById(fint_entityid);
        List<Fintech> fintechList = service.findAllFintech();
        FintechDto dto = new FintechDto();

        if(fintechData.isPresent()){
            for (Fintech fint : fintechList) {
                if(fint_entityid.equals(fint.getFint_entityid())){
                    dto.setFint_name(fint.getFint_name());
                    dto.setFint_desc(fint.getFint_desc());
                }
            }
            service.deleteFintechById(fint_entityid);
            return new ResponseEntity<>("Delete Success: " + "\n" + "Fintech: " + dto.getFint_name() + "\n"
            + "Description: " + dto.getFint_desc(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id tidak ditemukan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
}
