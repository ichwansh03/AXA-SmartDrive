package com.app.smartdrive.api.mapper.payment.UserAccounts;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;

import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import lombok.Data;


@Data
public class UserAccountsMapperResponse {
    public static UserAccountsDtoResponse convertEnityToDto(UserAccounts userAccounts, UserAccountsDtoRequests requests) {
        UserAccountsDtoResponse dtoUserAccounts = UserAccountsDtoResponse.builder()
                .usacAccountno(requests.getNoRekening())
                .usacDebet(requests.getNominall())
                .enumPaymentType(userAccounts.getEnumPaymentType())
                .build();
        return dtoUserAccounts;
    }
}
