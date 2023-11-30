package com.app.smartdrive.api.controllers.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.app.smartdrive.api.dto.payment.BanksIdForUserDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.Roles;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserRolesId;
import com.app.smartdrive.api.services.payment.implementation.BankServiceImpl;
import com.app.smartdrive.api.services.users.implementation.BusinessEntityImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class BanksController {
    private final BankServiceImpl service;
    private final BusinessEntityImpl serviceBusiness;

    @GetMapping("/banks/all")
    public ResponseEntity<?> getAllBanks(){
        List<Banks> newSteam = service.getAll();
        return new ResponseEntity<>(newSteam,HttpStatus.OK);
    }
    @GetMapping("/banks/{bank_entityid}")
    public ResponseEntity<?> getBanksById(@Valid @PathVariable("bank_entityid") Long bank_entityid){
       Banks newid = service.getById(bank_entityid);
       return new ResponseEntity<>(newid,HttpStatus.OK);
    }

    @PostMapping("/banks/add")
    public ResponseEntity<?> addBanks(@Valid @RequestParam(name = "bank_name", required = true) String bank_name,
    @RequestParam(name = "bank_desc",required = true) String bank_desc){

       Banks banks = service.addedBanks(bank_name, bank_desc);
       return new ResponseEntity<>(banks,HttpStatus.CREATED);
    }

    @PutMapping("/banks/{bank_entityid}/update")
    public ResponseEntity<?> updateBanks(@Valid @PathVariable("bank_entityid") Long bank_entityid, 
    @RequestParam(name = "bank_name", required = false)String bank_name,
    @RequestParam(name = "bank_desc", required = false)String bank_desc){
        Boolean newBank = service.updateBanks(bank_entityid,bank_name, bank_desc);
        return new ResponseEntity<>(newBank, HttpStatus.OK);
    }

    @DeleteMapping("/banks/delete/{bank_entityid}")
    public ResponseEntity<?> deleteBanks(@Valid @PathVariable("bank_entityid") Long bank_entityid){
        var deleteId = service.deleteBanks(bank_entityid);
        
        return new ResponseEntity<>(deleteId, HttpStatus.OK);
    }   
    @GetMapping("/banks/user/{bank_name}")
    public ResponseEntity<?> getUserBanks(@Valid @PathVariable("bank_name") String bank_name){
        BanksIdForUserDto getUser = service.getBanksUser(bank_name);
        return new ResponseEntity<>(getUser, HttpStatus.OK);

    } 
}
