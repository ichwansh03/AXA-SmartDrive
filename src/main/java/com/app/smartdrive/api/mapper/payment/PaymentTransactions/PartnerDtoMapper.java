//package com.app.smartdrive.api.mapper.payment.PaymentTransactions;
//
//import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;
//import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
//import com.app.smartdrive.api.entities.partner.Partner;
//import com.app.smartdrive.api.entities.payment.PaymentTransactions;
//
//import lombok.Data;
//
//@Data
//public class PartnerDtoMapper {
//    public static GenerateTransferResponse mapperPartnerDto(BatchPartnerInvoice partner, PaymentTransactions transactions){
//        GenerateTransferResponse dto = GenerateTransferResponse.builder()
//        .accountNo(partner.getAccountNo())
//        .invoiceNo(partner.getNo())
//        .paidDate(partner.getPaidDate())
//        .nominal(partner.getSubTotal())
//        .status(partner.getStatus())
//        .trxNo(transactions.getPatrTrxno())
//        .build();
//        return dto;
//    }
//
//}
