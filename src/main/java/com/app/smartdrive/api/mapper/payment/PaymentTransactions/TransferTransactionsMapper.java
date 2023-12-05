package com.app.smartdrive.api.mapper.payment.PaymentTransactions;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;

import lombok.Data;

@Data
public class TransferTransactionsMapper {
    public static TransferTransactionsResponse convertDtoRequestToResponse(TransferTransactionsRequest request){
        
        TransferTransactionsResponse dto = TransferTransactionsResponse.builder()
        .usac_accountno(request.getUsac_accountno())
        .patr_usac_accountNo_to(request.getPatr_usac_accountNo_to())
        .transfer(request.getTransfer())
        .patr_notes(request.getPatr_notes())
        .enumPayment(request.getEnumPayment())
        .patr_invoice_no(request.getPatr_invoice_no())
        .build();
        return dto;
        

    }
}
