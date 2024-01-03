package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateTransferResponse {
    private String invoiceNo;
    private String accountNo;
    private Enum status;
    private LocalDateTime paidDate;
    private BigDecimal nominal;
    private String trxNo;
    
}
