package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.GenerateTransactionsRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TypeTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import com.app.smartdrive.api.services.BaseService;

public interface PaymentTransactionsService extends BaseService<PaymentTransactions, String>{
    List<PaymentTransactions> findAllPaymentTransactions();
    TransaksiResponse transaksiByUser(TransactionsDtoRequests request);
    GenerateTransferResponse generateTransaksi(GenerateTransactionsRequests requests);
    // Boolean topupFintech(Long usac_id, Payment)
    // TransferTransactionsResponse transfer(Long usac_id, TransferTransactionsRequest request);
    // TopupFintechResponse topupFintech(TopupFintechRequests requests );
    // TypeTransactionsResponse typeTransactionsByEnumPayment (String enumPayment);
    
} 
