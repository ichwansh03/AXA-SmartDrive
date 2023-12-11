package com.app.smartdrive.api.mapper.payment.PaymentTransactions;

import java.time.LocalDateTime;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TypeTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;

import lombok.Data;

@Data
public class TypeTransactionsMapper {
    public static TypeTransactionsResponse convertEntityToDtoResponse(PaymentTransactions paymentTransactions){
        TypeTransactionsResponse dto = TypeTransactionsResponse.builder()
        .patr_trxno(paymentTransactions.getPatrTrxno())
        .patr_created_on(paymentTransactions.getPatr_created_on())
        .patr_debet(paymentTransactions.getPatr_debet())
        .patr_credit(paymentTransactions.getPatr_credit())
        .patr_usac_accountNo_from(paymentTransactions.getPatr_usac_accountNo_from())
        .patr_usac_accountNo_to(paymentTransactions.getPatr_usac_accountNo_to())
        .enumPayment(paymentTransactions.getPatr_type())
        .patr_trxno_rev(paymentTransactions.getPatrTrxnoRev())
        .build();

        return dto;
        
    }
}
