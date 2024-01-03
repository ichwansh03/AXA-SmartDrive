package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;


import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentTransactionsDtoResponse {
    private String patr_trxno;
    private LocalDateTime patr_created_on;
    private BigDecimal patr_debet;
    private BigDecimal patr_credit;
    private String patr_usac_accountNo_from;
    private String patr_usac_accountNo_to;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    private String patr_invoice_no;
    private String patr_notes;
    private String patr_trxno_rev;
}
