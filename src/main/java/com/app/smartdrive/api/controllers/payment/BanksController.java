package com.app.smartdrive.api.controllers.payment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.app.smartdrive.api.dto.payment.Request.PaymentOperasional.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Payment.PaymentDtoResponse;
import com.app.smartdrive.api.services.payment.PaymentService;

import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
@CrossOrigin
public class BanksController {
    private final PaymentService bankServiceImpl;
    private final BusinessEntityImpl serviceBusiness;

    @GetMapping("/banks/all")
    public ResponseEntity<?> getAllBanks(){
        List<PaymentDtoResponse> response = bankServiceImpl.getAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/banks/{bank_entityid}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> getBanksById( @PathVariable("bank_entityid") Long bank_entityid){
       PaymentDtoResponse resultDto = bankServiceImpl.getById(bank_entityid);
       return new ResponseEntity<>(resultDto,HttpStatus.OK);
    }

    @PostMapping("/banks/add")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> addBanks(@Valid @RequestBody PaymentRequestsDto requests){
        
        PaymentDtoResponse response = bankServiceImpl.addPayment(requests);
        
       
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/banks/update/{bank_entityid}")
    public ResponseEntity<?> updateBanks(@Valid @PathVariable("bank_entityid") Long bank_entityid, 
    @RequestBody PaymentRequestsDto requests){
        PaymentDtoResponse newBank = bankServiceImpl.updateById(bank_entityid,requests);
        return new ResponseEntity<>(newBank, HttpStatus.OK);
    }

    @DeleteMapping("/banks/delete/{bank_entityid}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> deleteBanks(@Valid @PathVariable("bank_entityid") Long bank_entityid){
        boolean deleteId = bankServiceImpl.deleteById(bank_entityid);
        return new ResponseEntity<>(deleteId, HttpStatus.OK);
    }   
    // @GetMapping("/banks/user/{bank_name}")
    // public ResponseEntity<?> getUserBanks(@Valid @PathVariable("bank_name") String bank_name){
    //     BanksIdForUserDtoResponse getUser = service.getBanksUser(bank_name);
    //     return new ResponseEntity<>(getUser, HttpStatus.OK);

    // } 
}
