package com.app.smartdrive.api.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactions, String> {

    
}