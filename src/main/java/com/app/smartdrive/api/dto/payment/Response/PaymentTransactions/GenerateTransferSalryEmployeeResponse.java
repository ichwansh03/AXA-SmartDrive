package com.app.smartdrive.api.dto.payment.Response.PaymentTransactions;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateTransferSalryEmployeeResponse {
    private Long emp_id;
    private String emp_name;
    private LocalDateTime created_on;
    private Double salary;
    private String accountno;
    private LocalDateTime transfer_date;
    private String transaction_no;
    // @Enumerated(EnumType.STRING)
    // private EnumClassPayment.EnumPayment enumPayment;

}
