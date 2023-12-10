package com.app.smartdrive.api.controllers.payment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupBankRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupBanksResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.services.payment.PaymentTransactionsService;
import com.app.smartdrive.api.services.payment.implementation.PaymentTransactionsImpl;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentTransactionsController {
    private final PaymentTransactionsService service;

    @GetMapping("/all")
    public ResponseEntity<?> findAllPaymentTransactions(){
        List<PaymentTransactions> findPayment = service.findAllPaymentTransactions();
        return new ResponseEntity<>(findPayment, HttpStatus.OK);
    }
    
    @PostMapping("/transactions/add")
    public ResponseEntity<?> addPaymentTransactions(@Valid @ModelAttribute PaymentTransactionsDto paymentTransactionsDto){
        PaymentTransactionsDto dto = service.addPaymentTransactions(paymentTransactionsDto);
        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/transactions/{patrTrxno}")
    public ResponseEntity<?> getIdPayment(@Valid @PathVariable("patrTrxno") String patrTrxno){
        PaymentTransactions pt = service.getById(patrTrxno);
        return new ResponseEntity<>(pt, HttpStatus.OK);
    }

    @PostMapping("/transactions/{usac_id}/topupBank")
    public ResponseEntity<?> topupBank(@Valid @PathVariable("usac_id") Long usac_id,
     @RequestBody TopupBankRequests requests){
        TopupBanksResponse topupBanks = service.topupBanks(usac_id, requests);
        return new ResponseEntity<>(topupBanks, HttpStatus.OK);
    }
    
    @PostMapping("/transactions/{usac_id}/transfer")
    public ResponseEntity<?> transfer(@Valid @PathVariable("usac_id") Long usac_id,
    @RequestBody TransferTransactionsRequest request){
        TransferTransactionsResponse transfer = service.transfer(usac_id, request);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }

    @PostMapping("/transactions/{usac_id}/topupFintech")
    public ResponseEntity<?> topupFintech(@Valid @PathVariable("usac_id") Long usac_id,
    @RequestBody TopupFintechRequests requests){
        TopupFintechResponse topupFintech = service.topupFintech(usac_id, requests);
        return new ResponseEntity<>(topupFintech, HttpStatus.OK);
    }

}
