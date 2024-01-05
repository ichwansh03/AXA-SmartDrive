package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsByUserDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.GenerateTransactionsRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.PaymentTransactionsDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiByUserDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;

public interface TransactionsService extends BasePaymentManagementService<PaymentTransactionsDtoResponse, String>{
    List<PaymentTransactions> findAllPaymentTransactions();
    TransaksiByUserDtoResponse transaksiByUser(TransactionsByUserDtoRequests request);
    GenerateTransferResponse generateTransaksi(GenerateTransactionsRequests requests);

} 