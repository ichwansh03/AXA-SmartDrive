package com.app.smartdrive.api.mapper.payment;

import com.app.smartdrive.api.dto.payment.Response.FintechDto;
import com.app.smartdrive.api.entities.payment.Fintech;

public class FintechMapper {
    public static FintechDto convertEntityToDto(Fintech fintech){
        FintechDto fintechDto = FintechDto.builder()
            .fint_entityid(fintech.getFint_entityid())
            .fint_name(fintech.getFint_name())
            .fint_desc(fintech.getFint_desc())
            .build();
        return fintechDto;
    }
}
