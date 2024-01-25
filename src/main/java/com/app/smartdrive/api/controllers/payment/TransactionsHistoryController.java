package com.app.smartdrive.api.controllers.payment;

import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.EmployeeSalaryHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.PaidPartnerHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.PremiHistoryDtoResponse;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.services.payment.TransactionsHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@CrossOrigin
public class TransactionsHistoryController {
    private final TransactionsHistoryService historyService;
    @GetMapping("/all/salaryHistory")
    public ResponseEntity<?> getAllSalary(){
        List<EmployeeSalaryHistoryDtoResponse> findAllSalary = historyService.getAllSuksesHistorySalary();
        return new ResponseEntity<>(findAllSalary, HttpStatus.OK);
    }

    @GetMapping("/all/PremiCreditNotPaid")
    public ResponseEntity<?> getAllPremiNotPaid(){
        List<PremiHistoryDtoResponse> listAllPremiNotPaid = historyService.getAllNotPaidPremi();
        return new ResponseEntity<>(listAllPremiNotPaid, HttpStatus.OK);
    }
    @GetMapping("/all/BpinStatusPaid")
    public ResponseEntity<?> getAllBpinStatusPaid(){
        List<PaidPartnerHistoryDtoResponse> listAllBpinStatusPaid = historyService.getAllBpinStatusPaid();
        return new ResponseEntity<>(listAllBpinStatusPaid, HttpStatus.OK);
    }

    @GetMapping("/all/PremiCreditPaid")
    public ResponseEntity<?> getAllPremiCreditPaid(){
        List<PremiHistoryDtoResponse> listAllPremiPaid = historyService.getAllPremiCreditPaid();
        return new ResponseEntity<>(listAllPremiPaid, HttpStatus.OK);
    }
}
