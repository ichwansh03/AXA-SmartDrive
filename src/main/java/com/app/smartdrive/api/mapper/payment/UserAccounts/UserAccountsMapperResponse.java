package com.app.smartdrive.api.mapper.payment.UserAccounts;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import lombok.Data;

@Data
public class UserAccountsMapperResponse {
    public static UserAccountsDtoResponse convertEnityToDto(UserAccountsDtoRequests requests){
        UserAccountsDtoResponse dtoUserAccounts = new UserAccountsDtoResponse();
        dtoUserAccounts.setUsac_accountno(requests.getNoRekening());
        dtoUserAccounts.setUsac_debet(requests.getNominall());
        dtoUserAccounts.setStatus("Success");
        return dtoUserAccounts;
    }
}
