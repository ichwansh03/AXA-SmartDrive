package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GenerateTransferResponse {
    private String invoiceNo;
    private String accountNo;
    private Enum status;
    private LocalDateTime paidDate;
    private Double nominal;
    private String trxNo;
    
}
