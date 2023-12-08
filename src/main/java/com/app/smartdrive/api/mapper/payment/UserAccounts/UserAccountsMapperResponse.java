package com.app.smartdrive.api.mapper.payment.UserAccounts;

import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import lombok.Data;

@Data
public class UserAccountsMapperResponse {
    public static UserAccountsDtoResponse convertEnityToDto(UserAccounts userAccounts){
        UserAccountsDtoResponse dtoUserAccounts = new UserAccountsDtoResponse();
        dtoUserAccounts.setUsac_accountno(userAccounts.getUsac_accountno());
        dtoUserAccounts.setUsac_debet(userAccounts.getUsac_debet());
        dtoUserAccounts.setUsac_credit(userAccounts.getUsac_credit());
        dtoUserAccounts.setEnumPaymentType(userAccounts.getEnumPaymentType());
        return dtoUserAccounts;
    }
}
