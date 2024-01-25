package com.app.smartdrive.api.mapper.payment.PaymentTransactions;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsByUserDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiByUserDtoResponse;

import lombok.Data;

@Data
public class TransaksiMapper {
    public static TransaksiByUserDtoResponse mapperTransactionsDto(String invoice, TransactionsByUserDtoRequests request){
       TransaksiByUserDtoResponse dto = TransaksiByUserDtoResponse.builder()
               .patrInvoiceNo(invoice)
               .enumPayment(request.getEnumPayment())
               .nominall(request.getNominall())
               .patrNotes(request.getPatrNotes())
               .patrUsacAccountNoTo(request.getToRekening())
               .usacAccountno(request.getNoRekening())
               .build();
        return dto;
    }
}
