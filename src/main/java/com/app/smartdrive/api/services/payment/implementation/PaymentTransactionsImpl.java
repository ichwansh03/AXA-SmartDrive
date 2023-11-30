package com.app.smartdrive.api.services.payment.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.mapper.payment.PaymentTransactionsMapper;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionsRepository;
import com.app.smartdrive.api.services.payment.PaymentTransactionsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentTransactionsImpl implements PaymentTransactionsService{
    
    private final PaymentTransactionsRepository repository;

    @Override
    public List<PaymentTransactions> findAllPaymentTransactions() {
        return repository.findAll();
    }

    @Override
    public PaymentTransactionsDto addPaymentTransactions(PaymentTransactionsDto paymentTransactionsDto) {
        PaymentTransactions pT = new PaymentTransactions();
        
        pT.setPatrTrxno(paymentTransactionsDto.getPatrTrxno());
        pT.setPatr_created_on(LocalDateTime.now());
        pT.setPatr_debet(paymentTransactionsDto.getPatr_debet());
        pT.setPatr_credit(paymentTransactionsDto.getPatr_credit());
        pT.setPatr_usac_accountNo_from(paymentTransactionsDto.getPatr_usac_accounntNo_from());
        pT.setPatr_usac_accountNo_to(paymentTransactionsDto.getPatr_usac_accountNo_to());
        pT.setEnumPayment(paymentTransactionsDto.getEnumPayment());
        pT.setPatr_invoice_no(paymentTransactionsDto.getPatr_invoice_no());
        pT.setPatr_notes(paymentTransactionsDto.getPatr_notes());
        pT.setPatrTrxnoRev(paymentTransactionsDto.getPatrTrxnoRev());
        repository.save(pT);

        PaymentTransactionsDto dto = PaymentTransactionsMapper.convertEntityToDto(pT);
        return dto;
    }

    

    @Override
    public List<PaymentTransactions> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentTransactions getById(String id) {
        // TODO Auto-generated method stub
        return repository.findById(id).get();
    }

    @Override
    public PaymentTransactions save(PaymentTransactions entity) {
        // TODO Auto-generated method stub
        return null;
    }

    

   

    

}
