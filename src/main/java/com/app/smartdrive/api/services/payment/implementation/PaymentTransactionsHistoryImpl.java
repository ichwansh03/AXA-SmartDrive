package com.app.smartdrive.api.services.payment.implementation;

import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.EmployeeSalaryHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.PaidPartnerHistoryDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.HistoryTransactions.PremiHistoryDtoResponse;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.services.payment.TransactionsHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTransactionsHistoryImpl implements TransactionsHistoryService {

    private final BatchEmployeeSalaryRepository employeeSalaryRepository;
    private final EmployeesRepository employeesRepository;
    private final SecrRepository servicePremiRepository;
    private final BatchPartnerInvoiceRepository partnerInvoiceRepository;
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

    @Override
    public List<PremiHistoryDtoResponse> getAllNotPaidPremi() {
        List<ServicePremiCredit> listAll = servicePremiRepository.findAllTrxnoIsNull();
        List<PremiHistoryDtoResponse> liatAllDtoResponse = new ArrayList<>();

        for (ServicePremiCredit premi: listAll) {
            PremiHistoryDtoResponse dtoResponse = PremiHistoryDtoResponse.builder()
                    .secrId(premi.getSecrId())
                    .secrServId(premi.getSecrServId())
                    .Year(premi.getSecrYear())
                    .DueDate(premi.getSecrDuedate())
                    .TransactionDate(premi.getSecrTrxDate())
                    .NumberTransactions(premi.getPaymentTransactions())
                    .build();
            liatAllDtoResponse.add(dtoResponse);
        }
        return liatAllDtoResponse;
    }

    @Override
    public List<PaidPartnerHistoryDtoResponse> getAllBpinStatusPaid() {
        List<BatchPartnerInvoice> listPartner = partnerInvoiceRepository.findAllBpinStatusPaid();
        List<PaidPartnerHistoryDtoResponse> listResponse = new ArrayList<>();
        for (BatchPartnerInvoice partner: listPartner) {
            PaidPartnerHistoryDtoResponse dtoPartner = PaidPartnerHistoryDtoResponse.builder()
                    .Nomor_Invoice(partner.getNo())
                    .CreatedOn(partner.getCreatedOn())
                    .Total(partner.getSubTotal())
                    .Tax(partner.getTax())
                    .Nomor_Rekening(partner.getAccountNo())
                    .Status(partner.getStatus())
                    .Tanggal_Pembayaran(partner.getPaidDate())
                    .Partner_entityid(partner.getPartner())
                    .Id_ServiceOrders(partner.getServiceOrders())
                    .build();
            listResponse.add(dtoPartner);
        }
        return listResponse;
    }
}
