package com.app.smartdrive.api.mapper.payment;

import com.app.smartdrive.api.dto.payment.BanksDto;
import com.app.smartdrive.api.entities.payment.Banks;

public class BanksMapper {
    public static BanksDto convertEntityToDto(Banks banks){
        BanksDto dtoBanks = new BanksDto();
        dtoBanks.setBank_name(banks.getBank_name());
        dtoBanks.setBank_desc(banks.getBank_desc());
        return dtoBanks;
    }
    
}
