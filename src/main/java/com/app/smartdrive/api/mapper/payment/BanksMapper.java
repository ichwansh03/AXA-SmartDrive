package com.app.smartdrive.api.mapper.payment;

import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.entities.payment.Banks;

public class BanksMapper {
    public static BanksDto convertEntityToDto(Banks banks){
        BanksDto banksDto = BanksDto.builder()
            .bank_entityid(banks.getBank_entityid())
            .bank_name(banks.getBank_name())
            .bank_desc(banks.getBank_desc())
            .build();
        return banksDto;
    }
}
