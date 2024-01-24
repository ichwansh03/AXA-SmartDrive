package com.app.smartdrive.api.dto.payment.Response.HistoryTransactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryHistoryDtoResponse {
    private Long empId;
    private String employeeName;
    private LocalDate createdOn;
    private BigDecimal salary;
    private String accountno;
    private LocalDateTime transferDate;
    private String transactionNo;
    private String status;
}
