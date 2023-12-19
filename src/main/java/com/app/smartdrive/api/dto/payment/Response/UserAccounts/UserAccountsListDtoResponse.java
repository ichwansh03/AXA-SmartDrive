package com.app.smartdrive.api.dto.payment.Response.UserAccounts;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserAccountsListDtoResponse {
    private String usac_accountno;
    private Double usac_debet;
    @Enumerated(EnumType.STRING)
    private EnumPaymentType tipePayment;
}
