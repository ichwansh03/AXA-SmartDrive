package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.PaymentTransactionsDto;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.services.BaseService;

public interface PaymentTransactionsService extends BaseService<PaymentTransactions, String>{
    PaymentTransactionsDto addPaymentTransactions(PaymentTransactionsDto paymentTransactionsDto);
    List<PaymentTransactions> findAllPaymentTransactions();
} 
