package com.app.smartdrive.api.dto.payment.Response.UserAccounts;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class UserAccountsDtoResponse {
    private String usac_accountno;
    private Double usac_debet;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPaymentType usac_type;


}
