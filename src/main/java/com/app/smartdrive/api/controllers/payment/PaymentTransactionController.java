package com.app.smartdrive.api.controllers.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.services.payment.implementation.PaymentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentTransactionController {
    private final PaymentServiceImpl service;

    @GetMapping("/paymentTransactions")
    public ResponseEntity<?> getAllPaymentTransactions(){
        return ResponseEntity.ok(service.getAll());
    }
    
}
