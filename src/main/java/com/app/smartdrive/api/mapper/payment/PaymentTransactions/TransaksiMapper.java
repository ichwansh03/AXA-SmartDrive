package com.app.smartdrive.api.mapper.payment.PaymentTransactions;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiResponse;

import lombok.Data;

@Data
public class TransaksiMapper {
    public static TransaksiResponse mapperTransactionsDto(TransactionsDtoRequests request){
        TransaksiResponse dto = TransaksiResponse.builder()
        .usac_accountno(request.getUsac_accountno())
        .patr_usac_accountNo_to(request.getPatr_usac_accountNo_to())
        .nominall(request.getNominall())
        .patr_notes(request.getPatr_notes())
        .enumPayment(request.getEnumPayment())
        .patr_invoice_no(request.getPatr_invoice_no())
        .build();
        return dto;
    }
}
