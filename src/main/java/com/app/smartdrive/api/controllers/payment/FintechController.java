package com.app.smartdrive.api.controllers.payment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.smartdrive.api.dto.payment.Request.PaymentOperasional.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Payment.PaymentDtoResponse;
import com.app.smartdrive.api.services.payment.PaymentService;
import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@CrossOrigin
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
