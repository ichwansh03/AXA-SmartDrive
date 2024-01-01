package com.app.smartdrive.api.services.payment;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksIdForUserDtoResponse;
import com.app.smartdrive.api.services.master.MasterService;


public interface BankService extends MasterService<BanksDtoResponse, Long> {

    BanksDtoResponse addBankss(BanksDtoRequests banksDtoRequests);
    Boolean updateBanks(Long bank_entityid,BanksDtoResponse banksDto);
    Boolean deleteBanks(Long bank_entityid);
    BanksIdForUserDtoResponse getBanksUser(String bank_name);
    public BanksDtoResponse getBankById(Long id);
} 