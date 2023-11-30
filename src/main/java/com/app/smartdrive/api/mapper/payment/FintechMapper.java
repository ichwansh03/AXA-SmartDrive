package com.app.smartdrive.api.mapper.payment;

import com.app.smartdrive.api.dto.payment.FintechDto;
import com.app.smartdrive.api.entities.payment.Fintech;

public class FintechMapper {
    public static FintechDto convertEntityToDto(Fintech fintech){
        FintechDto dtoFintech = new FintechDto();
        dtoFintech.setFint_name(fintech.getFint_name());
        dtoFintech.setFint_desc(fintech.getFint_desc());
        return dtoFintech;
    }
}
