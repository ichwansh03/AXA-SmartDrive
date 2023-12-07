package com.app.smartdrive.api.mapper.payment.PaymentTransactions;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupBankRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupBanksResponse;

import lombok.Data;

@Data
public class TopupBanksMapper {
    public static TopupBanksResponse mapperTransactionsDto(TopupBankRequests request){
        TopupBanksResponse dto = TopupBanksResponse.builder()
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
