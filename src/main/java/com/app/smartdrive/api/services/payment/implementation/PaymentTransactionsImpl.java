package com.app.smartdrive.api.services.payment.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.PaymentTransactionsDtoResponse;
import com.app.smartdrive.api.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.SaldoIsNotEnoughException;
import com.app.smartdrive.api.Exceptions.TypeTransaksiNotFoundException;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsByUserDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.GenerateTransactionsRequests;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiByUserDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.GenerateTransferResponse;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPayment;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransaksiMapper;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionsRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;
import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
import com.app.smartdrive.api.services.payment.TransactionsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentTransactionsImpl implements TransactionsService {
    private final EntityManager entityManager;
    private final PaymentTransactionsRepository repository;
    private final UserAccountsRepository userAccountsRepository;
    private final BatchEmployeeSalaryRepository employeeSalaryRepository;
    private final BatchPartnerInvoiceRepository partnerInvoiceRepository;
    private final BatchPartnerInvoiceService serviceInvoicePartner;
    private final BatchEmployeeSalaryService serviceEmployeSalary;
    private final SecrRepository secrRepository;


    public String dateTimeFormatter(LocalDateTime b) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = b.format(formatter);
        return formattedDate;
    }

    public String dateFormatter(LocalDate b) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = b.format(formatter);
        return formattedDate;
    }

    private String generateTrxNo(LocalDateTime timeNow) {
        return "trx" + dateTimeFormatter(timeNow) + "000" + setIdForTrxno();
    }


    private synchronized int getIdFromSequence() {
        int trxSequence = repository.countTrxno();
        trxSequence++;
        return trxSequence;
    }

    private String setIdForTrxno(){
        int c = getIdFromSequence();
        String str1 = Integer.toString(c);
        return str1;
    }


    private String uuidInvoice(){
        UUID uuid = UUID.randomUUID();
        Long uuidd = uuid.getMostSignificantBits();
        String idUnique = uuidd.toString();
        String invoiceAutomate = "INV-" + idUnique;
        return invoiceAutomate;
    }


    private void automateIdAndCreateEntities(BigDecimal nominall, String noRekening, String toRekening, String notes,
                                             EnumPayment enumPayment, String invoice) {

        List<PaymentTransactions> listPayment = repository.findAll();

        String trxNo = generateTrxNo(LocalDateTime.now());
        String trxNoRev = repository.findLastOptional();

        if(listPayment.isEmpty()){
            PaymentTransactions transactions = PaymentTransactions.builder()
                    .patrTrxno(trxNo)
                    .patrTrxnoRev(null)
                    .build();
        }
        PaymentTransactions transactions = PaymentTransactions.builder()
                .patrTrxno(trxNo)
                .patrTrxnoRev(trxNoRev)
                .patrCreatedOn(LocalDateTime.now())
                .patrUsacAccountNoFrom(noRekening)
                .patrUsacAccountNoTo("-")
                .patrDebet(nominall)
                .patrCredit(BigDecimal.ZERO)
                .patrType(enumPayment)
                .patrInvoiceNo(invoice)
                .patrNotes(notes)
                .build();

        repository.save(transactions);

        PaymentTransactions transactions2 = PaymentTransactions.builder()
                .patrTrxno(generateTrxNo(LocalDateTime.now()))
                .patrCreatedOn(LocalDateTime.now())
                .patrUsacAccountNoFrom("-")
                .patrUsacAccountNoTo(toRekening)
                .patrCredit(nominall)
                .patrDebet(BigDecimal.ZERO)
                .patrType(enumPayment)
                .patrNotes(notes)
                .patrInvoiceNo(invoice)
                .patrTrxnoRev(transactions.getPatrTrxno())
                .build();
        repository.save(transactions2);

    }
    private void checkSaldoHandle(BigDecimal saldoSender, BigDecimal nominal, EnumPayment enumPayment) {
        if (saldoSender == null || saldoSender.compareTo(BigDecimal.ZERO) == 0) {
            throw new SaldoIsNotEnoughException("Saldo anda:" + " Rp." + saldoSender
                    + ", Failed: " + enumPayment + ", Harap melakukan pengisian saldo!");
        } else if (saldoSender.compareTo(nominal) < 0) {
            throw new SaldoIsNotEnoughException("Saldo anda:" + " Rp." + saldoSender
                    + ", Failed: " + enumPayment + ", Saldo anda kurangg!");
        }
    }



    @Override
    public TransaksiByUserDtoResponse transaksiByUser(TransactionsByUserDtoRequests request) {
        TransaksiByUserDtoResponse dto = new TransaksiByUserDtoResponse();

        automateIdAndCreateEntities(request.getNominall(), request.getNoRekening(),
                request.getToRekening(), request.getPatrNotes(), request.getEnumPayment(), uuidInvoice());

        switch (request.getEnumPayment()){
            case TOPUP_BANK, TOPUP_FINTECH, TRANSFER:
                if(checkValidationNoAccount(request.getNoRekening(),request.getToRekening(),userAccountsRepository)){
                    calculationTransaksiDebetProcess(request.getNoRekening(),
                            request.getToRekening(),request.getNominall(),userAccountsRepository);
                }else{
                    checkErrorAccount(request.getNoRekening(),
                            request.getToRekening(), userAccountsRepository);
                }
                break;
            default:
                throw new TypeTransaksiNotFoundException("Harap memilih tipe transaksi yang benar!");
        }
        dto = TransaksiMapper.mapperTransactionsDto(uuidInvoice(),request);
        return dto;
    }

    @Override
    public GenerateTransferResponse generateTransaksi(GenerateTransactionsRequests request) {

        GenerateTransferResponse dtoGenerateTransaksi = new GenerateTransferResponse();
        dtoGenerateTransaksi = handlePaymentTypeGenerateTransactions(request);

        return dtoGenerateTransaksi;
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
                List<String> listInvoice = new ArrayList<>();
                int countPartner = partnerInvoiceRepository.countBpin(request.getToRekening());
                int tempPartner = 0;
                BigDecimal countSubTotal = BigDecimal.ZERO;

                while(tempPartner<countPartner){
                    if (checkValidationNoAccount(request.getNoRekening(), request.getToRekening(), userAccountsRepository)) {
                        BatchPartnerInvoice partnerInvoice = partnerInvoiceRepository.findByBpinAccountNo(request.getToRekening());
                        Double nominalWithTaxInDouble = partnerInvoice.getSubTotal() - (partnerInvoice.getTax());
                        BigDecimal nominalWithTax = new BigDecimal(Double.toString(nominalWithTaxInDouble));
                        BigDecimal saldoSenderPartner = userAcc.getUsacDebet();
                        LocalDateTime time = LocalDateTime.now();
                        String invoice = partnerInvoice.getNo();
                        BatchPartnerInvoice partnerr = new BatchPartnerInvoice();

                        checkSaldoHandle(saldoSenderPartner, nominalWithTax, request.getTipePayment());

                        transactions.setPatrTrxno(generateTrxNo(LocalDateTime.now()));
                        transactions2.setPatrTrxno(transactions.getPatrTrxno());
                        partnerr.setAccountNo(partnerInvoice.getAccountNo());
                        partnerr.setSubTotal(partnerInvoice.getSubTotal());
                        partnerr.setTax(partnerInvoice.getTax());
                        partnerr.setPartner(partnerInvoice.getPartner());
                        partnerr.setNo(partnerInvoice.getNo());
                        partnerr.setServiceOrders(partnerInvoice.getServiceOrders());
                        partnerr.setPaidDate(time);
                        partnerr.setStatus(BpinStatus.PAID);
                        partnerr.setPaymentTransactions(transactions2);
                        partnerr.setCreatedOn(time);

                        transactions.setPatrDebet(nominalWithTax);
                        transactions2.setPatrInvoiceNo(partnerInvoice.getNo());
                        transactions2.setPatrCredit(nominalWithTax);

                        automateIdAndCreateEntities(nominalWithTax, request.getNoRekening(),
                                request.getToRekening(), request.getNotes(), request.getTipePayment(), partnerInvoice.getNo());

                        calculationTransaksiDebetProcess(request.getNoRekening(),
                                request.getToRekening(), nominalWithTax, userAccountsRepository);

                        partnerInvoiceRepository.saveAndFlush(partnerr);

                        dtoGenerate.setAccountNo(partnerInvoice.getAccountNo());
                        dtoGenerate.setInvoiceNo(listInvoice);
                        dtoGenerate.setPaidDate(partnerInvoice.getPaidDate());
                        dtoGenerate.setNominal(nominalWithTax);
                        dtoGenerate.setStatus(partnerInvoice.getStatus());
                        dtoGenerate.setTrxNo(transactions2.getPatrTrxno());
                        listInvoice.add(invoice);
                        tempPartner++;
                        countSubTotal = countSubTotal.add(nominalWithTax);
                    }else{
                        checkErrorAccount(request.getNoRekening(),request.getToRekening(),userAccountsRepository);
                    }
                }dtoGenerate.setTotalNominal(countSubTotal);
                break;

            case PREMI:
                if(checkValidationNoAccount(request.getNoRekening(), request.getToRekening(), userAccountsRepository)){
                    BigDecimal nominalPremi = premiCredit.getSecrPremiDebet();
                    BigDecimal saldoSenderPremi = userAcc.getUsacDebet();
                    String invoiceNo = "SERV-" + premiCredit.getSecrId();

                    checkSaldoHandle(saldoSenderPremi, nominalPremi, request.getTipePayment());

                    transactions.setPatrTrxno(generateTrxNo(LocalDateTime.now()));
                    transactions2.setPatrTrxno(transactions.getPatrTrxno());
                    transactions.setPatrDebet(nominalPremi);
                    transactions2.setPatrInvoiceNo(invoiceNo);
                    transactions2.setPatrCredit(nominalPremi);

                    automateIdAndCreateEntities(nominalPremi, request.getNoRekening(),
                            request.getToRekening(), request.getNotes(), request.getTipePayment(), invoiceNo);

                    premiCredit.setPaymentTransactions(transactions2);
                    premiCredit.setSecrTrxDate(LocalDateTime.now());
                    secrRepository.save(premiCredit);


                    calculationTransaksiDebetProcess(request.getNoRekening(),
                            request.getToRekening(),nominalPremi, userAccountsRepository);

                    dtoGenerate.setAccountNo(request.getToRekening());
                    dtoGenerate.setInvoiceNo(List.of(invoiceNo));
                    dtoGenerate.setNominal(nominalPremi);
                    dtoGenerate.setStatus(EnumModuleServiceOrders.SemiStatus.PAID);
                    dtoGenerate.setTotalNominal(nominalPremi);
                    dtoGenerate.setPaidDate(LocalDateTime.now());
                    dtoGenerate.setTrxNo(transactions2.getPatrTrxno());
                }else {
                    checkErrorAccount(request.getNoRekening(), request.getToRekening(), userAccountsRepository);
                }
                break;
            case SALARY:
                List<String> listGt = new ArrayList<>();
                int count = employeeSalaryRepository.countBesaPatrTrxno(request.getToRekening());
                int i = 0;
                BigDecimal countSalary = BigDecimal.ZERO;

                while(i<count){
                    if (checkValidationNoAccount(request.getNoRekening(), request.getToRekening(), userAccountsRepository)) {
                        BatchEmployeeSalary employeeSalary = employeeSalaryRepository.findBesaAccountNumber(request.getToRekening());

                        BigDecimal nominalSalary = employeeSalary.getBesaTotalSalary();
                        BigDecimal saldoSender = userAcc.getUsacDebet();
                        LocalDate createdDateSalary = employeeSalary.getBesaCreatedDate();
                        String invoiceSalary = "SAL-" + dateFormatter(createdDateSalary);


                        checkSaldoHandle(saldoSender, nominalSalary, request.getTipePayment());

                        transactions.setPatrTrxno(generateTrxNo(LocalDateTime.now()));
                        transactions2.setPatrTrxno(transactions.getPatrTrxno());
                        employeeSalary.setEmsTrasferDate(LocalDateTime.now());
                        employeeSalary.setBesaPatrTrxno(generateTrxNo(LocalDateTime.now()));
                        employeeSalary.setBesaPaidDate(LocalDateTime.now());
                        employeeSalary.setBesaModifiedDate(LocalDateTime.now());


                        automateIdAndCreateEntities(nominalSalary, request.getNoRekening(),
                                request.getToRekening(), request.getNotes(), request.getTipePayment(), invoiceSalary);

                        calculationTransaksiDebetProcess(request.getNoRekening(), request.getToRekening(),nominalSalary, userAccountsRepository);

                        employeeSalaryRepository.saveAndFlush(employeeSalary);
                        dtoGenerate.setAccountNo(employeeSalary.getBesaAccountNumber());
                        dtoGenerate.setInvoiceNo(listGt);

                        dtoGenerate.setStatus(BpinStatus.PAID);
                        dtoGenerate.setNominal(nominalSalary);
                        dtoGenerate.setPaidDate(employeeSalary.getBesaPaidDate());
                        dtoGenerate.setTrxNo(transactions2.getPatrTrxno());
                        listGt.add(invoiceSalary);

                        countSalary = countSalary.add(nominalSalary);
                        i++;
                    }else{
                        checkErrorAccount(request.getNoRekening(), request.getToRekening(), userAccountsRepository);
                    }
                }dtoGenerate.setTotalNominal(countSalary);
                break;
            default:
                throw new TypeTransaksiNotFoundException("Harap memilih tipe transaksi yang benar!");
        }
        return dtoGenerate;
    }

    @Override
    public PaymentTransactionsDtoResponse getById(String trxNo) {
        Optional<PaymentTransactions> transactions = repository.findById(trxNo);
        PaymentTransactionsDtoResponse response = TransactionMapper.mapEntityToDto(transactions,PaymentTransactionsDtoResponse.class);
        return response;
    }

    @Override
    public List<PaymentTransactionsDtoResponse> getAll() {
        List<PaymentTransactions> listTransactions = repository.findAll();
        List<PaymentTransactionsDtoResponse> listResponse = TransactionMapper.mapEntityListToDtoList(
                listTransactions, PaymentTransactionsDtoResponse.class);
        return listResponse;
    }


}
