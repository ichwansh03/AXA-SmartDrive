package com.app.smartdrive.api.dto.payment.Response.HistoryTransactions;

import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremiHistoryDtoResponse {
    private Long secrId;
    private Long secrServId;
    private String Year;
    private BigDecimal PremiDebet;
    private LocalDateTime TransactionDate;
    private LocalDateTime DueDate;
    private PaymentTransactions NumberTransactions;
}
