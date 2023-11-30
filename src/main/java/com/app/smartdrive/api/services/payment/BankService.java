package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.dto.payment.Response.BanksIdForUserDto;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.services.BaseService;


public interface BankService extends BaseService<BanksDto, Long>{
    Boolean updateBanks(Long bank_entityid,BanksDto banksDto);
    Boolean deleteBanks(Long bank_entityid);
    BanksIdForUserDto getBanksUser(String bank_name);
} 