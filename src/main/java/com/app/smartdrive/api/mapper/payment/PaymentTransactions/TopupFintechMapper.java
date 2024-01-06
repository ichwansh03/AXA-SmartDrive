//package com.app.smartdrive.api.mapper.payment.PaymentTransactions;
//
//import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
//
//import lombok.Data;
//
//@Data
//public class TopupFintechMapper {
//    public static TopupFintechResponse mapperTopupFintech(TopupFintechRequests requests){
//        TopupFintechResponse dto = TopupFintechResponse.builder()
//        .usac_accountno(requests.getUsac_accountno())
//        .patr_usac_accountNo_to(requests.getPatr_usac_accountNo_to())
//        .nominall(requests.getNominall())
//        .patr_notes(requests.getPatr_notes())
//        .enumPayment(requests.getEnumPayment())
//        .patr_invoice_no(requests.getPatr_invoice_no())
//        .build();
//        return dto;
//    }
//}
