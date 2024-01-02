package com.app.smartdrive.api.services.payment;

import java.util.List;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.mapper.payment.UserAccounts.UserAccountsMapperResponse;

public interface UserAccountsService extends BasePaymentManagementService<UserAccountsMapperResponse, Long>  {
    UserAccountsDtoResponse addDebet(UserAccountsDtoRequests requests);
} 
   

