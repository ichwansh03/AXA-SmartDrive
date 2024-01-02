package com.app.smartdrive.api.controllers.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.Request.Banks.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Request.Fintech.FintechDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.services.payment.PaymentService;
import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class FintechController {
    private final PaymentService fintechServiceImpl;
    private final BusinessEntityImpl serviceBusiness;

    @GetMapping("/fintech/all")
    public ResponseEntity<?> getAllFintech(){
        List<PaymentDtoResponse> listFintech = fintechServiceImpl.getAll();
        return new ResponseEntity<>(listFintech, HttpStatus.OK);
    }

    @GetMapping("/fintech/{fint_entityid}")
    public ResponseEntity<?> getFintechById(@Valid @PathVariable("fint_entityid") Long fint_entityid){
        PaymentDtoResponse newFintechId = fintechServiceImpl.getById(fint_entityid);
        return new ResponseEntity<>(newFintechId, HttpStatus.OK);
    }


    @PostMapping("/fintech/add")
    public ResponseEntity<?> addedFintech(@Valid @RequestBody PaymentRequestsDto requestsDto){
        PaymentDtoResponse resulDto = fintechServiceImpl.addPayment(requestsDto);
        return new ResponseEntity<>(resulDto, HttpStatus.OK);
    }

    @PutMapping("/fintech/{fint_entityid}/update")
    public ResponseEntity<?> updateFintech(@Valid @PathVariable("fint_entityid") Long fint_entityid, 
    @RequestBody PaymentRequestsDto requestsDto){
        PaymentDtoResponse updateFintechDto = fintechServiceImpl.updateById(fint_entityid, requestsDto);
        return new ResponseEntity<>(updateFintechDto, HttpStatus.OK);
    }

    @DeleteMapping("/fintech/{fint_entityid}/delete")
    public ResponseEntity<?> deleteFintech(@Valid @PathVariable("fint_entityid") Long fint_entityid){
        Boolean deleteFintech = fintechServiceImpl.deleteById(fint_entityid);
        return new ResponseEntity<>(deleteFintech, HttpStatus.OK);
    }


   
}
