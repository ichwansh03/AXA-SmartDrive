package com.app.smartdrive.api.controllers.payment;

import java.util.List;
import java.util.stream.Collectors;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsByUserDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.EmployeeSalaryHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.PaymentTransactionsDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiByUserDtoResponse;
import com.app.smartdrive.api.services.payment.TransactionsHistoryService;
import com.app.smartdrive.api.services.payment.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.GenerateTransactionsRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;


import com.app.smartdrive.api.entities.payment.PaymentTransactions;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentTransactionsController {
    private final TransactionsService service;
    private final TransactionsHistoryService historyService;

    @GetMapping("/all")
    public ResponseEntity<?> findAllPaymentTransactions(){
        List<PaymentTransactions> findPayment = service.findAllPaymentTransactions();
        return new ResponseEntity<>(findPayment, HttpStatus.OK);
    }

    @GetMapping("/all/salaryHistory")
    public ResponseEntity<?> getAllSalary(){
        List<EmployeeSalaryHistoryDtoResponse> findAllSalary = historyService.getAllSuksesHistorySalary();
        return new ResponseEntity<>(findAllSalary, HttpStatus.OK);
    }
    @GetMapping("/transactions/{patrTrxno}")
    public ResponseEntity<?> getIdPayment(@Valid @PathVariable("patrTrxno") String patrTrxno){
        PaymentTransactionsDtoResponse pt = service.getById(patrTrxno);
        return new ResponseEntity<>(pt, HttpStatus.OK);
    }

    @PostMapping("/transactions/transaksi")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> topupBank(@Valid @RequestBody TransactionsByUserDtoRequests requests){
        TransaksiByUserDtoResponse topupBanks = service.transaksiByUser(requests);
        return new ResponseEntity<>(topupBanks, HttpStatus.OK);
    }

    @PostMapping("/transactions/generateTransfer")
    public ResponseEntity<?> generateTransfer(@Valid @RequestBody GenerateTransactionsRequests request){
        GenerateTransferResponse generateTransfer = service.generateTransaksi(request);
        return new ResponseEntity<>(generateTransfer, HttpStatus.OK);
    }
}
