package com.app.smartdrive.api.services.payment.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.SaldoIsNotEnoughException;
import com.app.smartdrive.api.Exceptions.TypeTransaksiNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.GenerateTransactionsRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.BatchPartnerInvoiceResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TypeTransactionsResponse;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPayment;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransaksiMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.BatchPartnerInvoiceMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.PartnerDtoMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TopupFintechMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransferTransactionsMapper;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionsRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;
import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
import com.app.smartdrive.api.services.payment.PaymentTransactionsService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentTransactionsImpl implements PaymentTransactionsService {
    private final EntityManager entityManager;

    private final PaymentTransactionsRepository repository;
    private final UserAccountsRepository userAccountsRepository;
    private final BatchEmployeeSalaryRepository employeeSalaryRepository;
    private final BatchPartnerInvoiceRepository partnerInvoiceRepository;
    private final BatchPartnerInvoiceService serviceInvoicePartner;
    private final BatchEmployeeSalaryService serviceEmployeSalary;
    private final SecrRepository secrRepository;

    public PaymentTransactions addaPY(PaymentTransactions paymentTransactions) {
        entityManager.persist(paymentTransactions);
        return paymentTransactions;
    }

    @Override
    public List<PaymentTransactions> findAllPaymentTransactions() {
        return repository.findAll();
    }

    private LocalDateTime timeNow(){
        LocalDateTime time = LocalDateTime.now();
        return time;
    }

    public String dateTimeFormatter(LocalDateTime b) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = b.format(formatter);
        return formattedDate;
    }

    private String generateTrxNo(LocalDateTime timeNow) {
        return "trx" + dateTimeFormatter(timeNow) + "000" + getIdFromSequence();
    }

   

    private synchronized int getIdFromSequence() {
        int b = repository.countTrxno();
        int trxSequence = b += 1;
        return trxSequence;
    }

   
    private String uuidInvoice(){
        UUID uuid = UUID.randomUUID();
        Long uuidd = uuid.getMostSignificantBits();
        String idUnique = uuidd.toString();
        String invoiceAutomate = "INV-" + idUnique; 
        return invoiceAutomate;
    }

    private void automateIdAndCreateEntities(Double nominall, String noRekening, String toRekening, String notes,
        EnumPayment enumPayment, String invoice) {

        PaymentTransactions transactions = new PaymentTransactions();
        PaymentTransactions transactions2 = new PaymentTransactions();
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions reversal = repository.findByPatrTrxno(transactions.getPatrTrxno());

       

        if (listPayment.isEmpty()) {
            transactions.setPatrTrxno(generateTrxNo(timeNow()));
            transactions.setPatrTrxnoRev(null);
        } else {
            for (PaymentTransactions py : listPayment) {
                if (py.getPatrTrxnoRev() == null) {
                    int newB = getIdFromSequence() - 1;
                    transactions.setPatrTrxno(generateTrxNo(timeNow()));
                    transactions.setPatrTrxnoRev("trx" + dateTimeFormatter(timeNow()) + "000" + newB);
                } else if (reversal != null && py.getPatrTrxnoRev() != null) {
                    int newC = getIdFromSequence() - 1;
                    transactions.setPatrTrxno(generateTrxNo(timeNow()));
                    transactions.setPatrTrxnoRev("trx" + dateTimeFormatter(timeNow()) + "000" + newC);
                }
            }
        }
        transactions.setPatr_created_on(timeNow());
        transactions.setPatr_usac_accountNo_from(noRekening);
        transactions.setPatr_usac_accountNo_to("-");
        transactions.setPatr_debet(nominall);
        transactions.setPatr_type(enumPayment);
        transactions.setPatr_invoice_no(invoice);
        transactions.setPatr_notes(notes);
        repository.save(transactions);
       
        transactions2.setPatrTrxno(generateTrxNo(timeNow()));
        transactions2.setPatr_created_on(timeNow());
        transactions2.setPatr_usac_accountNo_from("-");
        transactions2.setPatr_usac_accountNo_to(toRekening);
        transactions2.setPatr_credit(nominall);
        transactions2.setPatr_type(enumPayment);
        transactions2.setPatr_notes(notes);
        transactions2.setPatr_invoice_no(invoice);
        transactions2.setPatrTrxnoRev(transactions.getPatrTrxno());

        repository.saveAndFlush(transactions2);

    }
    private void checkSaldoHandle(Double saldoSender, Double nominal, EnumPayment enumPayment) {
        if (saldoSender == null || saldoSender == 0.0) {
            throw new SaldoIsNotEnoughException("Saldo anda:" + " Rp." + saldoSender
                    + ", Failed: " + enumPayment + ", Harap melakukan pengisian saldo!");
        } else if (saldoSender < nominal) {
            throw new SaldoIsNotEnoughException("Saldo anda:" + " Rp." + saldoSender
                    + ", Failed: " + enumPayment + ", Saldo anda kurangg!");
        }
    }

  
    @Override
    public TransaksiResponse transaksiByUser(TransactionsDtoRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        TransaksiResponse dto = new TransaksiResponse();
    
        automateIdAndCreateEntities(request.getNominall(), request.getUsac_accountno(),
                request.getPatr_usac_accountNo_to(), request.getPatr_notes(), request.getEnumPayment(), uuidInvoice());

        if (request.getEnumPayment() == EnumPayment.TOPUP_BANK ||
                request.getEnumPayment() == EnumPayment.TRANSFER ||
                request.getEnumPayment() == EnumPayment.TOPUP_FINTECH) {

            for (UserAccounts userAccounts : listAcc) {
                if (request.getUsac_accountno().equals(userAccounts.getUsac_accountno())) {

                    Double saldoSender = userAccounts.getUsac_debet();
                    EnumPayment typeTransaksi = request.getEnumPayment();
                    Double saldoNominal = request.getNominall();
                    checkSaldoHandle(saldoSender, saldoNominal, typeTransaksi);
                    Double totalSaldoSender = saldoSender - saldoNominal;
                    userAccounts.setUsac_debet(totalSaldoSender);
                            listAcc.stream()
                    .filter(user -> request.getPatr_usac_accountNo_to().equals(user.getUsac_accountno()))
                    .forEach(user -> user.setUsac_debet(
                        Optional.ofNullable(user.getUsac_debet()).orElse(0.0) + saldoNominal));
                userAccountsRepository.saveAll(listAcc);

                        }
            }
        }
        
        else {
            throw new EntityNotFoundException("Failed Transaksi, Harap Pilih Tipe Transaksi yang Benar");
        }
        dto = TransaksiMapper.mapperTransactionsDto(uuidInvoice(),request);
        return dto;
    }

    @Override
    public GenerateTransferResponse generateTransaksi(GenerateTransactionsRequests request) {
        
        GenerateTransferResponse dtoGenerate = new GenerateTransferResponse();
        dtoGenerate = handlePaymentTypeGenerateTransactions(request);

        return dtoGenerate;
    }


    private void handleUpdateDebetTransactionsUserAccount(List<UserAccounts> listAcc, 
    GenerateTransactionsRequests request, Double nominal ){
        listAcc.stream()
            .filter(user -> request.getToRekening().equals(user.getUsac_accountno()))
            .forEach(user -> user.setUsac_debet(
                Optional.ofNullable(user.getUsac_debet()).orElse(0.0) + nominal));
        userAccountsRepository.saveAll(listAcc);
    }

    private GenerateTransferResponse handlePaymentTypeGenerateTransactions( GenerateTransactionsRequests request) {
        UserAccounts userAcc = userAccountsRepository.findByAccounts(request.getNoRekening());
        PaymentTransactions transactions = new PaymentTransactions();
        PaymentTransactions transactions2 = new PaymentTransactions();
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        List<BatchEmployeeSalary> listEmployeSalary = serviceEmployeSalary.getAllTransNull();
        List<BatchPartnerInvoice> listPartnerInvoice = serviceInvoicePartner.getAllInvoiceNotPaid();
        ServicePremiCredit premiCredit = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(),
                LocalDateTime.now().plusMonths(1));
        GenerateTransferResponse dtoGenerate = new GenerateTransferResponse();

        switch (request.getTipePayment()) {
            case PAID_PARTNER:
                for (BatchPartnerInvoice partner : listPartnerInvoice) {
                    if (request.getToRekening().equals(partner.getAccountNo())) {
                        Double nominalWithTax = partner.getSubTotal() - partner.getTax();
                        Double saldoSenderPartner = userAcc.getUsac_debet();
                        Double totalSaldoSenderPartner = saldoSenderPartner - nominalWithTax;
                        LocalDateTime time = LocalDateTime.now();
                        BatchPartnerInvoice partnerr = new BatchPartnerInvoice();

                        checkSaldoHandle(saldoSenderPartner, nominalWithTax, request.getTipePayment());

                        transactions.setPatrTrxno(generateTrxNo(timeNow()));
                        transactions2.setPatrTrxno(transactions.getPatrTrxno());
                        partnerr.setNo(partner.getNo());
                        partnerr.setServiceOrders(partner.getServiceOrders());
                        partnerr.setPaidDate(time);
                        partnerr.setStatus(BpinStatus.PAID);
                        partnerr.setPaymentTransactions(transactions2);
                        partnerr.setCreatedOn(time);
                        userAcc.setUsac_debet(totalSaldoSenderPartner);
                        transactions.setPatr_debet(nominalWithTax);
                        transactions2.setPatr_invoice_no(partner.getNo());
                        transactions2.setPatr_credit(nominalWithTax);

                        automateIdAndCreateEntities(nominalWithTax, request.getNoRekening(),
                        request.getToRekening(), request.getNotes(), request.getTipePayment(), partner.getNo());

                        handleUpdateDebetTransactionsUserAccount(listAcc, request, nominalWithTax);
                        
                        partnerInvoiceRepository.saveAndFlush(partnerr);
                            dtoGenerate.setAccountNo(partner.getAccountNo());
                            dtoGenerate.setInvoiceNo(partner.getNo());
                            dtoGenerate.setPaidDate(partner.getPaidDate());
                            dtoGenerate.setNominal(nominalWithTax);
                            dtoGenerate.setStatus(partner.getStatus());
                            dtoGenerate.setTrxNo(transactions2.getPatrTrxno());

                    }
                }
                break;
            case PREMI:
                Double nominalPremi = premiCredit.getSecrPremiDebet().doubleValue();
                Double saldoSenderPremi = userAcc.getUsac_debet();
                Double totalSaldoSenderPremi = saldoSenderPremi - nominalPremi;
                String invoiceNo = "SERV-" + premiCredit.getSecrId();

                checkSaldoHandle(saldoSenderPremi, nominalPremi, request.getTipePayment());
                transactions.setPatrTrxno(generateTrxNo(timeNow()));
                transactions2.setPatrTrxno(transactions.getPatrTrxno());
                transactions.setPatr_debet(nominalPremi);
                transactions2.setPatr_invoice_no(invoiceNo);
                transactions2.setPatr_credit(nominalPremi);
                automateIdAndCreateEntities(nominalPremi, request.getNoRekening(),
                request.getToRekening(), request.getNotes(), request.getTipePayment(), invoiceNo);
                premiCredit.setPaymentTransactions(transactions2);
                userAcc.setUsac_debet(totalSaldoSenderPremi);

               handleUpdateDebetTransactionsUserAccount(listAcc, request, nominalPremi);
               
                    dtoGenerate.setAccountNo(request.getNoRekening());
                    dtoGenerate.setInvoiceNo(invoiceNo);
                    dtoGenerate.setNominal(nominalPremi);
                    dtoGenerate.setStatus(EnumModuleServiceOrders.SemiStatus.PAID);
                    dtoGenerate.setPaidDate(LocalDateTime.now());
                    dtoGenerate.setTrxNo(transactions2.getPatrTrxno());
                
                break;
            case SALARY:
          
                for (BatchEmployeeSalary employee : listEmployeSalary) {
                    if (request.getToRekening().equals(employee.getBesaAccountNumber())) {
                        Double nominalSalary = employee.getBesaTotalSalary().doubleValue();
                        Double saldoSender = userAcc.getUsac_debet();
                        Double totalSaldoSender = saldoSender - nominalSalary;
                        LocalDateTime createdDateSalary = employee.getBatchEmployeeSalaryId()
                                .getBesaCreatedDate();
                        String invoiceSalary = "SAL-" + dateTimeFormatter(createdDateSalary);

                        checkSaldoHandle(saldoSender, nominalSalary, request.getTipePayment());
                       
                        transactions.setPatrTrxno(generateTrxNo(timeNow()));
                        transactions2.setPatrTrxno(transactions.getPatrTrxno());
                        employee.setEmsTrasferDate(LocalDateTime.now());
                        employee.setBesaPatrTrxno(transactions2.getPatrTrxno());
                        employee.setBesaPaidDate(LocalDateTime.now());
                        employee.setBesaModifiedDate(LocalDateTime.now());
                        userAcc.setUsac_debet(totalSaldoSender);

                        automateIdAndCreateEntities(nominalSalary, request.getNoRekening(),
                request.getToRekening(), request.getNotes(), request.getTipePayment(), invoiceSalary);

                        handleUpdateDebetTransactionsUserAccount(listAcc, request, nominalSalary);

                        employeeSalaryRepository.saveAndFlush(employee);
                        dtoGenerate.setAccountNo(employee.getBesaAccountNumber());
                        dtoGenerate.setInvoiceNo("SAL-" + createdDateSalary);
                        dtoGenerate.setNominal(nominalSalary);
                        dtoGenerate.setPaidDate(employee.getBesaPaidDate());
                        dtoGenerate.setTrxNo(transactions2.getPatrTrxno());
                    }
                }
                break;
            default:
                throw new TypeTransaksiNotFoundException("Harap memilih tipe transaksi yang benar!");
        }
       return dtoGenerate;
    }

    @Override
    public List<PaymentTransactions> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentTransactions getById(String id) {
        // TODO Auto-generated method stub
        return repository.findById(id).get();
    }

    @Override
    public PaymentTransactions save(PaymentTransactions entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
