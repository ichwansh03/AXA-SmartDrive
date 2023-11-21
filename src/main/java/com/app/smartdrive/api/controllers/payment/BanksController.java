package com.app.smartdrive.api.controllers.payment;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.BanksDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.payment.implementation.BankServiceImpl;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class BanksController {
    private final BankServiceImpl service;

    @GetMapping("/banks/all")
    public ResponseEntity<?> getAllBanks(){
        List<Banks> newSteam = service.findAllBank();

        List<BanksDto> listDto = newSteam.stream().map(banks ->{
            BanksDto dto = new BanksDto();
            dto.setBank_name(banks.getBank_name());
            dto.setBank_desc(banks.getBank_desc());
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }
    @GetMapping("/banks/{bank_entityid}")
    public ResponseEntity<?> getBanksById(@Valid @PathVariable("bank_entityid") Long bank_entityid){
        Optional<Banks> banksData = service.getBankById(bank_entityid);
        List<Banks> banksList = service.findAllBank();
        BanksDto dto = new BanksDto();
        if(banksData.isPresent()){
            for (Banks  bank: banksList) {
                if(bank_entityid.equals(bank.getBank_entityid())){
                    dto.setBank_name(bank.getBank_name());
                    dto.setBank_desc(bank.getBank_desc());
                }
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id tidak ditemukan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/banks/add")
    public ResponseEntity<?> addBanks(@Valid 
    @RequestParam(name = "bankName" , required = true ) String bank_name,
    @RequestParam(name = "description", required = true) String bank_desc){

        Banks banks = new Banks();
        banks.setBank_name(bank_name);
        banks.setBank_desc(bank_desc);
        banks.setBank_entityid(Long.valueOf(2));
        service.addBanks(banks);
        
        BanksDto banksDto = new BanksDto();
        banksDto.setBank_name(banks.getBank_name());
        banksDto.setBank_desc(banks.getBank_desc());
    
        return new ResponseEntity<>(banksDto,HttpStatus.CREATED);
    }

    @PutMapping("/banks/{bank_entityid}/update")
    public ResponseEntity<?> updateBanks(@Valid @PathVariable("bank_entityid") Long bank_entityid,
    @RequestParam(name = "bankName", required = true) String bank_name,
    @RequestParam(name = "description", required = true) String description){
        Optional<Banks> banksData = service.findById(bank_entityid);
        if(banksData.isPresent()){
            Banks bankss = banksData.get();
            bankss.setBank_name(bank_name);
            bankss.setBank_desc(description);
            service.addBanks(bankss);

            BanksDto banksDto = new BanksDto();
            banksDto.setBank_name(bankss.getBank_name());
            banksDto.setBank_desc(bankss.getBank_desc());
            return new ResponseEntity<>(banksDto,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Bank entity id Not Found for ID: " +bank_entityid, HttpStatus.OK);
        }
    }

    @DeleteMapping("/banks/delete/{bank_entityid}")
    public ResponseEntity<?> deleteBanks(@Valid @PathVariable("bank_entityid") Long bank_entityid){
        Optional<Banks> banksData = service.findById(bank_entityid);
        List<Banks> banksList = service.findAllBank();
        
        BanksDto banksDto = new BanksDto();
        if(banksData.isPresent()){
            for (Banks bankc : banksList) {
                if(bank_entityid.equals(bankc.getBank_entityid())){
                    banksDto.setBank_name(bankc.getBank_name());
                    banksDto.setBank_desc(bankc.getBank_desc());
                }
            }
            service.deleteById(bank_entityid);
            return new ResponseEntity<>("Delete Success: " + "\n" +
            "Bank: "+ banksDto.getBank_name() + "\n" + "Description: " +
            banksDto.getBank_desc(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Tidak ada id tersebut",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
}
