package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;

import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.UserAccounts.UserAccountsDtoRequestsFirst;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.UserAccounts.UserAccountsListDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.services.BaseService;

public interface UserAccountsService extends BaseService<UserAccounts, Long>{
    List<UserAccountsListDtoResponse> listDtoResponses();
    UserAccountsListDtoResponse getIdUser(Long usac_id);
    UserAccountsDtoResponse addDebit(UserAccountsDtoRequests userAccountsDtoRequests);
    Boolean deleteUserAccountsByNoRek(UserAccountsDtoRequestsFirst request);
} 
   

