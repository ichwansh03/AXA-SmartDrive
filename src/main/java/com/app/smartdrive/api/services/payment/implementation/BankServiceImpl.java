package com.app.smartdrive.api.services.payment.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.repositories.payment.BanksRepository;
import com.app.smartdrive.api.services.payment.BankService;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final BanksRepository banksRepository;

    @Override
    public Banks addBanks(Banks banks) {
        return banksRepository.save(banks);
    }

    @Override
    public List<Banks> findAllBank() {
        return banksRepository.findAll();
    }

    @Override
    public void removeBankName(String bank_name) {
        // TODO Auto-generated method stub
        
    }
    

  

    @Override
    public <T> T createBanks(T entity) {
        if(entity instanceof Banks){
            Banks banks = (Banks) entity;
            banksRepository.save(banks);
            return (T) banks;
        }
        else{
             return null;
        }
       
    }

    @Override
    public Optional<Banks> findById(Long bank_entityid) {
        // TODO Auto-generated method stub
        return banksRepository.findById(bank_entityid);
    }

    @Override
    public void deleteById(Long bank_entityid) {
        // TODO Auto-generated method stub
        banksRepository.deleteByID(bank_entityid);
        
    }

    @Override
    public Optional<Banks> getBankById(Long bank_entityid) {
        // TODO Auto-generated method stub
        return banksRepository.findById(bank_entityid);
    }

    

    

    
   

    

    
    
    
}
