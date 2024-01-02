package com.app.smartdrive.api.controllers.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.Banks.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.payment.PaymentService;

import com.app.smartdrive.api.services.payment.implementation.BankServiceImpl;
import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
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
