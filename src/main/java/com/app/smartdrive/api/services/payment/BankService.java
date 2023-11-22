package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.payment.Banks;


public interface BankService{

    List<Banks> findAllBank();
    Banks addBanks(Banks banks);
    void removeBankName(String bank_name);
    <T> T createBanks(T entity);
    Optional<Banks> findById(Long bank_entityid);
    void deleteById(Long bank_entityid);
    Optional<Banks> getBankById(Long bank_entityid);


    
    
} 