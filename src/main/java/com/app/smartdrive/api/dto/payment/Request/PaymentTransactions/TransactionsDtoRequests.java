package com.app.smartdrive.api.dto.payment.Request.PaymentTransactions;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsDtoRequests {
    
    @NotBlank(message = "Tidak boleh kosong")
    private String usac_accountno;

    @NotBlank(message = "Tidak boleh kosong")
    private String patr_usac_accountNo_to;
   
    @NotNull(message = "Tidak boleh kosong nominalnya")
    private Double nominall;
    
    @NotBlank(message = "Tidak boleh kosong")
    private String patr_notes;

    @NotNull(message = "Silahkan pilih tipe transaksi terlebih dahulu: TOPUP_BANK / TOPUP_FINTECH / TRANSFER / SALARY / PREMI / CLAIM_EVENT / PAID_PARTNER")
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    
    @NotBlank(message = "Tidak boleh kosong")
    private String patr_invoice_no;
}
