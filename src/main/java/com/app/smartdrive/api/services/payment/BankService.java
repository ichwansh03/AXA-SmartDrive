package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.BanksDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.services.BaseService;


public interface BankService extends BaseService<Banks, Long>{ 
    List<BanksDto> findById(Long id);
    public BanksDto addBanks(BanksDto banksDto);
    List<BanksDto> updateBanks(Long bank_entityid,BanksDto banksDto);
    Optional<Banks>  getId(Long id);
    List<BanksDto> getAAll();
    Boolean deleteBanks(Long bank_entityid);
} 