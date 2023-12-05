package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechBankRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupBanksFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import com.app.smartdrive.api.services.BaseService;

public interface PaymentTransactionsService extends BaseService<PaymentTransactions, String>{
    PaymentTransactionsDto addPaymentTransactions(PaymentTransactionsDto paymentTransactionsDto);
    List<PaymentTransactions> findAllPaymentTransactions();
    TopupBanksFintechResponse topupBanksFintech(Long usac_id,TopupFintechBankRequests request);
    // Boolean topupFintech(Long usac_id, Payment)
    TransferTransactionsResponse transfer(Long usac_id, TransferTransactionsRequest request);
    
} 
