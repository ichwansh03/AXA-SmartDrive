package com.app.smartdrive.api.controllers.payment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.payment.PaymentTransactionsDto;
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

        List<PaymentTransactionsDto> listDto = findPayment.stream().map(payment -> {
            PaymentTransactionsDto dto = new PaymentTransactionsDto();
            dto.setPatrTrxno(payment.getPatrTrxno());
            dto.setPatr_created_on(payment.getPatr_created_on());
            dto.setPatr_debet(payment.getPatr_debet());
            dto.setPatr_credit(payment.getPatr_credit());
            dto.setPatr_usac_accounntNo_from(payment.getPatr_usac_accountNo_from());
            dto.setPatr_usac_accountNo_to(payment.getPatr_usac_accountNo_to());
            dto.setEnumPayment(payment.getEnumPayment());
            dto.setPatr_invoice_no(payment.getPatr_invoice_no());
            dto.setPatr_notes(payment.getPatr_notes());
            dto.setPatrTrxnoRev(payment.getPatrTrxnoRev());
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(listDto, HttpStatus.OK);
    }
    
    @PostMapping("/transactions/add")
    public ResponseEntity<?> addPy(@Valid @ModelAttribute PaymentTransactionsDto paymentTransactionsDto){
        PaymentTransactionsDto dto = service.addPaymentTransactions(paymentTransactionsDto);
        
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/transactions/{patrTrxno}")
    public ResponseEntity<?> getIdPayment(@Valid @PathVariable("patrTrxno") String patrTrxno){
        PaymentTransactions pt = service.getById(patrTrxno);
        return new ResponseEntity<>(pt, HttpStatus.OK);
    }
    
}
