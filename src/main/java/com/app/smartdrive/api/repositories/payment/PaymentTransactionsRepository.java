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

    @Query(value = "SELECT TOP(1) * FROM payment.payment_transactions ORDER BY patr_created_on DESC ", nativeQuery = true) 
    String findLastOptional();
    
    @Query(value = "SELECT COUNT(patr_trxno) FROM payment.payment_transactions", nativeQuery = true)
    int countTrxno();

    // @Query(value = "SELECT patr_trxno FROM payment.payment_transactions WHERE patr_trxno IS NULL")
    // int checkTableEmpty();
    

    PaymentTransactions findByPatrTrxno(String patrTrxno);
    PaymentTransactions findByPatrTrxnoRev(String patrTrxnoRev);
    

    
} 
