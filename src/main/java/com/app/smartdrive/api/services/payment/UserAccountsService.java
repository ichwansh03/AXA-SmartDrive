package com.app.smartdrive.api.services.payment;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.NoRekAccDtoRequest;
import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;

public interface UserAccountsService extends BasePaymentManagementService<UserAccountsDtoResponse, Long>  {
    UserAccountsDtoResponse addDebet(UserAccountsDtoRequests requests);

    UserAccountsDtoResponse checkSaldo(NoRekAccDtoRequest requests);

    Boolean deleteUserAccountByNorek(NoRekAccDtoRequest request);
} 
   

