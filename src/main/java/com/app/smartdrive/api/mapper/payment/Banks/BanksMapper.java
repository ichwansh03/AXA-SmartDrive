package com.app.smartdrive.api.mapper.payment.Banks;

import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;

public class BanksMapper {
    public static BanksDtoResponse convertEntityToDto(Banks banks){
        BanksDtoResponse banksDto = BanksDtoResponse.builder()
            .bank_entityid(banks.getBank_entityid())
            .bank_name(banks.getBank_name())
            .bank_desc(banks.getBank_desc())
            .build();
        return banksDto;
    }
}
