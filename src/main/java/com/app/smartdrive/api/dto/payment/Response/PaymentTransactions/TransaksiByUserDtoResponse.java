package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransaksiByUserDtoResponse {
    private String usac_accountno;
    private String patr_usac_accountNo_to;
    private BigDecimal nominall;
    private String patr_notes;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    private String patr_invoice_no;
}
