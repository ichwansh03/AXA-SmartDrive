package com.app.smartdrive.api.services.payment.implementation;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.EmployeeSalaryHistoryDtoResponse;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.services.payment.TransactionsHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTransactionsHistoryImpl implements TransactionsHistoryService {

    private final BatchEmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeesRepository employeesRepository;
    @Override
    public List<EmployeeSalaryHistoryDtoResponse> getAllSuksesHistorySalary() {
        List<BatchEmployeeSalary> listSalary = employeeSalaryRepository.listEmployeePaidSalary();
        List<EmployeeSalaryHistoryDtoResponse> response = new ArrayList<>();

        for (BatchEmployeeSalary user: listSalary) {
            EmployeeSalaryHistoryDtoResponse not = EmployeeSalaryHistoryDtoResponse.builder()
                    .emp_id(user.getBesaEmpEntityid())
                    .employee_name(user.getEmployees().getEmpName())
                    .created_on(user.getBesaCreatedDate())
                    .salary(user.getBesaTotalSalary())
                    .accountno(user.getBesaAccountNumber())
                    .transfer_date(user.getEmsTrasferDate())
                    .transaction_no(user.getBesaPatrTrxno())
                    .status("Sukses Paid")
                    .build();
            response.add(not);
        }
        return response;
    }
}
