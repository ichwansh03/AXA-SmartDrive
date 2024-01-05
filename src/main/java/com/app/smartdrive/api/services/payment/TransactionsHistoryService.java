package com.app.smartdrive.api.services.payment;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.EmployeeSalaryHistoryDtoResponse;

import java.util.List;

public interface TransactionsHistoryService {
    List<EmployeeSalaryHistoryDtoResponse> getAllSuksesHistorySalary();

}
