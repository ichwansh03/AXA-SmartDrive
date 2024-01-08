package com.app.smartdrive.api.dto.payment.Response.UserAccounts;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountsDtoResponse{
    private String usacAccountno;
    private BigDecimal usacDebet;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPaymentType enumPaymentType;

}
