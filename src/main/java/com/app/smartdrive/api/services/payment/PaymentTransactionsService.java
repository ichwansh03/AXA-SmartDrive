package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.GenerateTransactionsRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.services.master.MasterService;

public interface PaymentTransactionsService {
    List<PaymentTransactions> findAllPaymentTransactions();
    TransaksiResponse transaksiByUser(TransactionsDtoRequests request);
    GenerateTransferResponse generateTransaksi(GenerateTransactionsRequests requests);
    // void createTransactionsFrom(String noRekening, EnumClassPayment.EnumPayment EnumPayment, String notes, String toRekening, String invoice, Double debet);
    // void createTransactionsTo(String noRekening, EnumClassPayment.EnumPayment EnumPayment, String notes, String toRekening, String invoice, Double debet);
    // Boolean topupFintech(Long usac_id, Payment)
    // TransferTransactionsResponse transfer(Long usac_id, TransferTransactionsRequest request);
    // TopupFintechResponse topupFintech(TopupFintechRequests requests );
    // TypeTransactionsResponse typeTransactionsByEnumPayment (String enumPayment);
    
} 
