package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;

public interface PaymentTransactionsService {

    List<PaymentTransactions> findAllPaymentTransactions();
} 
