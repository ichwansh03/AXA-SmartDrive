package com.app.smartdrive.api.mapper.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;

import lombok.Data;

@Data
public class PaymentTransactionsMapper {
    public static PaymentTransactionsDto convertEntityToDto(PaymentTransactions paymentTransactions){
        PaymentTransactionsDto paymentDto = new PaymentTransactionsDto();
        paymentDto.setPatrTrxno(paymentTransactions.getPatrTrxno());
        paymentDto.setPatr_created_on(LocalDateTime.now());
        paymentDto.setPatr_debet(paymentTransactions.getPatr_debet());
        paymentDto.setPatr_credit(paymentTransactions.getPatr_credit());
        paymentDto.setPatr_usac_accounntNo_from(paymentTransactions.getPatr_usac_accountNo_from());
        paymentDto.setPatr_usac_accountNo_to(paymentTransactions.getPatr_usac_accountNo_to());
        paymentDto.setEnumPayment(paymentTransactions.getEnumPayment());
        paymentDto.setPatr_invoice_no(paymentTransactions.getPatr_invoice_no());
        paymentDto.setPatr_notes(paymentTransactions.getPatr_notes());
        paymentDto.setPatrTrxnoRev(paymentTransactions.getPatrTrxnoRev());

        return paymentDto;
    }
}
