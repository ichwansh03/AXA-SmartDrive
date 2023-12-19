package com.app.smartdrive.api.mapper.payment.UserAccounts;


import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsListDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;

import lombok.Data;

@Data
public class UserAccountsListAllMapperResponse {
    public static UserAccountsListDtoResponse convertEntityToDto(UserAccounts user){
        UserAccountsListDtoResponse userAcc = new UserAccountsListDtoResponse();
        userAcc.setUsac_accountno(user.getUsac_accountno());
        userAcc.setUsac_debet(user.getUsac_debet());
        userAcc.setTipePayment(user.getEnumPaymentType());
        return userAcc;
    }
}
