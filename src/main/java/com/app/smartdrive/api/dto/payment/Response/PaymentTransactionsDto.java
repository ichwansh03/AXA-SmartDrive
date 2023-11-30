package com.app.smartdrive.api.dto.payment.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PaymentTransactionsDto {

    // buat dua dto, 1 untuk transer 
    // buat seqeuence table
    private String patrTrxno; // acc  number, aaccoun to, nominal, notes, enumoayment (topup, transfer), invoiceno, -> bikin method transfer
    private LocalDateTime patr_created_on;
    private Double patr_debet;
    private Double patr_credit;
    private Long patr_usac_accounntNo_from;
    private Long patr_usac_accountNo_to;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    private String patr_invoice_no;
    private String patr_notes;
    private String patrTrxnoRev;
    // selesesai transaksi, harus update 
    // mehtod isi saldo, method touup di useraccounts
    // method transfer

    // bank dan fintech, tipe datanya row json (request body)

    // method isi saldo dan topup, nanti jadi method yang langusng isi ke ketika user accounts dibuat


    // rekening axa

    // format txno: ada di mokup,
}
