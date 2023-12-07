package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.services.BaseService;


public interface BankService extends BaseService<BanksDtoResponse, Long>{

    BanksDtoResponse addBankss(BanksDtoRequests banksDtoRequests);
    Boolean updateBanks(Long bank_entityid,BanksDtoResponse banksDto);
    Boolean deleteBanks(Long bank_entityid);
    BanksIdForUserDtoResponse getBanksUser(String bank_name);
} 