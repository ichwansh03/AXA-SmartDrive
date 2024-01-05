package com.app.smartdrive.api.mapper.payment.PaymentTransactions;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsByUserDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiByUserDtoResponse;

import lombok.Data;

@Data
public class TransaksiMapper {
    public static TransaksiByUserDtoResponse mapperTransactionsDto(String invoice, TransactionsByUserDtoRequests request){
        TransaksiByUserDtoResponse dto = TransaksiByUserDtoResponse.builder()
        .usac_accountno(request.getUsac_accountno())
        .patr_usac_accountNo_to(request.getPatr_usac_accountNo_to())
        .nominall(request.getNominall())
        .patr_notes(request.getPatr_notes())
        .enumPayment(request.getEnumPayment())
        .patr_invoice_no(invoice)
        .build();
        return dto;
    }
}
