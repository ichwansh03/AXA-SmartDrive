package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;


import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PaymentTransactionsDtoResponse {
    private String patrTrxno;
    private LocalDateTime patrCreatedOn;
    private BigDecimal patrDebet;
    private BigDecimal patrCredit;
    private String patrUsacAccountNoFrom;
    private String patrUsacAccountNoTo;
    @Enumerated(EnumType.STRING)
    private EnumClassPayment.EnumPayment enumPayment;
    private String patrInvoiceNo;
    private String patrNotes;
    private String patrTrxnoRev;
}
