package com.app.smartdrive.api.dto.payment.Response.UserAccounts;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserAccountsDtoResponse extends UserAccountsListDtoResponse{
    private String status;

    

}

