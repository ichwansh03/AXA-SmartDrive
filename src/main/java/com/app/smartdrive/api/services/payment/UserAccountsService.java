package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.engine.validationcontext.BaseBeanValidationContext;

import com.app.smartdrive.api.dto.payment.Request.UserAccountsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.UserAccountsDtoResponse;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.services.BaseService;

public interface UserAccountsService extends BaseService<UserAccounts, Long>{
    List<UserAccountsDtoResponse> listDtoResponses();
    UserAccountsDtoResponse getIdUser(Long usac_id);
    Boolean addDebitCredit(Long usac_id, UserAccountsDtoRequests userAccountsDtoRequests);
    Boolean updateDebitCredit(Long usac_id, UserAccountsDtoRequests userAccountsDto);
    Boolean deleteUAById(Long usac_id);
} 
   

