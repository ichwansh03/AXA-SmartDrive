package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

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
    private Long emp_id;
    private String employee_name;
    private LocalDate created_on;
    private BigDecimal salary;
    private String accountno;
    private LocalDateTime transfer_date;
    private String transaction_no;
    private String status;
}
