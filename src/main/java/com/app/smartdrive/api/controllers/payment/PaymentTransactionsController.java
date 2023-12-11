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

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiResponse;
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
    
    @GetMapping("/transactions/{patrTrxno}")
    public ResponseEntity<?> getIdPayment(@Valid @PathVariable("patrTrxno") String patrTrxno){
        PaymentTransactions pt = service.getById(patrTrxno);
        return new ResponseEntity<>(pt, HttpStatus.OK);
    }

    @PostMapping("/transactions/transaksi")
    public ResponseEntity<?> topupBank(@Valid @RequestBody TransactionsDtoRequests requests){
        TransaksiResponse topupBanks = service.transaksi(requests);
        return new ResponseEntity<>(topupBanks, HttpStatus.OK);
    }
}
