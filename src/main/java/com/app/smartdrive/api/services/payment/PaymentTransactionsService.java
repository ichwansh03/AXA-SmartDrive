package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupBankRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupBanksResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import com.app.smartdrive.api.services.BaseService;

public interface PaymentTransactionsService extends BaseService<PaymentTransactions, String>{
    PaymentTransactionsDto addPaymentTransactions(PaymentTransactionsDto paymentTransactionsDto);
    List<PaymentTransactions> findAllPaymentTransactions();
    TopupBanksResponse topupBanks(Long usac_id,TopupBankRequests request);
    // Boolean topupFintech(Long usac_id, Payment)
    TransferTransactionsResponse transfer(Long usac_id, TransferTransactionsRequest request);
    TopupFintechResponse topupFintech(Long usac_id, TopupFintechRequests requests );
    
} 
