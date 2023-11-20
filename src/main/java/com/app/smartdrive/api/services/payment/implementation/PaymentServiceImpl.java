package com.app.smartdrive.api.services.payment.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionRepository;
import com.app.smartdrive.api.services.payment.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    
    private final PaymentTransactionRepository paymentTransactionRepository;

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<PaymentTransactions> getAll() {
        // TODO Auto-generated method stub
        return paymentTransactionRepository.findAll();
    }

    @Override
    public PaymentTransactions getById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentTransactions save(PaymentTransactions entity) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
