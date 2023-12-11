package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class TypeTransactionsResponse {
    private String patr_trxno;
    private LocalDateTime patr_created_on;
    private Double patr_debet;
    private Double patr_credit;
    private String patr_usac_accountNo_from;
    private String patr_usac_accountNo_to;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    private String patr_invoice_no;
    private String patr_notes;
    private String patr_trxno_rev;
}
