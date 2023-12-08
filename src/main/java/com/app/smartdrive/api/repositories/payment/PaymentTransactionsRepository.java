package com.app.smartdrive.api.repositories.payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;

@Repository
public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransactions,String>{

    @Query(value = "SELECT TOP(1) * FROM payment.payment_transactions ORDER BY patr_trxno DESC ", nativeQuery = true) 
    Optional<PaymentTransactions> findLastOptional();
    

    
} 
