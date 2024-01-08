package com.app.smartdrive.api.services.payment;

import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.EmployeeSalaryHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.PaidPartnerHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.PremiHistoryDtoResponse;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;

import java.util.List;

public interface TransactionsHistoryService {
    List<EmployeeSalaryHistoryDtoResponse> getAllSuksesHistorySalary();
    List<PremiHistoryDtoResponse> getAllNotPaidPremi();

    List<PaidPartnerHistoryDtoResponse> getAllBpinStatusPaid();

    List<PremiHistoryDtoResponse> getAllPremiCreditPaid();

}
