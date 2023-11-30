package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.BanksIdForUserDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.services.BaseService;


public interface BankService extends BaseService<Banks, Long>{
    Banks addedBanks(String bank_name, String bank_desc); 
    Boolean updateBanks(Long bank_entityid,String bank_name, String bank_desc);
    Boolean deleteBanks(Long bank_entityid);
    BanksIdForUserDto getBanksUser(String bank_name);
} 