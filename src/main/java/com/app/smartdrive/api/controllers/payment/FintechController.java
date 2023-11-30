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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        List<Fintech> listFintech = service.getAll();
        return new ResponseEntity<>(listFintech, HttpStatus.OK);
    }

    @GetMapping("/fintech/{fint_entityid}")
    public ResponseEntity<?> getFintechById(@Valid @PathVariable("fint_entityid") Long fint_entityid){
        Fintech newFintechId = service.getById(fint_entityid);
        return new ResponseEntity<>(newFintechId, HttpStatus.OK);
    }

    @PostMapping("/fintech/add")
    public ResponseEntity<?> addFintech(@Valid @RequestParam(name = "fint_name", required = true) String fint_name,
    @RequestParam(name = "fint_desc", required = true) String fint_desc){
        Fintech addFintech = service.addedFintech(fint_name, fint_desc);
        return new ResponseEntity<>(addFintech, HttpStatus.OK);
    }

    @PutMapping("/fintech/{fint_entityid}/update")
    public ResponseEntity<?> updateFintech(@Valid @PathVariable("fint_entityid") Long fint_entityid, 
    @RequestParam(name = "fint_name", required = false)String fint_name,
    @RequestParam(name = "fint_desc", required = false)String fint_desc){
        Boolean updateFintechDto = service.updateFintech(fint_entityid, fint_name, fint_desc);
        return new ResponseEntity<>(updateFintechDto, HttpStatus.OK);
    }

    @DeleteMapping("/fintech/{fint_entityid}/delete")
    public ResponseEntity<?> deleteFintech(@Valid @PathVariable("fint_entityid") Long fint_entityid){
        Boolean deleteFintech = service.deleteFintech(fint_entityid);
        return new ResponseEntity<>(deleteFintech, HttpStatus.OK);
    }


   
}
