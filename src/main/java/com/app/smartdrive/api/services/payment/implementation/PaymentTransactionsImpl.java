package com.app.smartdrive.api.services.payment.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionsRepository;
import com.app.smartdrive.api.services.payment.PaymentTransactionsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionsImpl implements PaymentTransactionsService{
    
    private final PaymentTransactionsRepository repository;

    @Override
    public List<PaymentTransactions> findAllPaymentTransactions() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    

}
