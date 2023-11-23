package com.app.smartdrive.api.dto.payment;

import java.util.Date;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PaymentTransactionsDto {
    
    private String patr_trxno;
    private Date patr_created_on;
    private Double patr_debet;
    private Double patr_credit;
    private Long patr_usac_accounntNo_from;
    private Long patr_usac_accountNo_to;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    private String patr_invoice_no;
    private String patr_notes;
    private String patr_trxno_rev;
}
