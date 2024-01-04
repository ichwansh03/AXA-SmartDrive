package com.app.smartdrive.api.services.payment.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.PaymentTransactionsDtoResponse;
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
import java.util.UUID;

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

    public PaymentTransactions addaPY(PaymentTransactions paymentTransactions) {
        entityManager.persist(paymentTransactions);
        return paymentTransactions;
    }

    @Override
    public List<PaymentTransactions> findAllPaymentTransactions() {
        return repository.getAllPaymentTransactions();
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

        String trxNo = generateTrxNo(timeNow());
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
                .patr_created_on(timeNow())
                .patr_usac_accountNo_from(noRekening)
                .patr_usac_accountNo_to("-")
                .patr_debet(nominall)
                .patr_credit(BigDecimal.ZERO)
                .patr_type(enumPayment)
                .patr_invoice_no(invoice)
                .patr_notes(notes)
                .build();

        repository.save(transactions);

        PaymentTransactions transactions2 = PaymentTransactions.builder()
                .patrTrxno(generateTrxNo(timeNow()))
                .patr_created_on(timeNow())
                .patr_usac_accountNo_from("-")
                .patr_usac_accountNo_to(toRekening)
                .patr_credit(nominall)
                .patr_debet(BigDecimal.ZERO)
                .patr_type(enumPayment)
                .patr_notes(notes)
                .patr_invoice_no(invoice)
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
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        TransaksiByUserDtoResponse dto = new TransaksiByUserDtoResponse();

        automateIdAndCreateEntities(request.getNominall(), request.getUsac_accountno(),
                request.getPatr_usac_accountNo_to(), request.getPatr_notes(), request.getEnumPayment(), uuidInvoice());

        if (request.getEnumPayment() == EnumPayment.TOPUP_BANK ||
                request.getEnumPayment() == EnumPayment.TRANSFER ||
                request.getEnumPayment() == EnumPayment.TOPUP_FINTECH) {


            for (UserAccounts userAccounts : listAcc) {
                if (request.getUsac_accountno().equals(userAccounts.getUsac_accountno())) {

                    BigDecimal saldoSender = userAccounts.getUsac_debet();
                    EnumPayment typeTransaksi = request.getEnumPayment();
                    BigDecimal saldoNominal = request.getNominall();
                    checkSaldoHandle(saldoSender, saldoNominal, typeTransaksi);
                    BigDecimal totalSaldoSender = saldoSender.subtract(saldoNominal);
                    userAccounts.setUsac_debet(totalSaldoSender);

                    handleUpdateDebetTransactionsUserAccount(listAcc, request.getPatr_usac_accountNo_to(), saldoNominal);
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
    String toRekening, BigDecimal nominal ){
        listAcc.stream()
            .filter(user -> toRekening.equals(user.getUsac_accountno()))
            .forEach(user -> user.setUsac_debet(
                Optional.ofNullable(user.getUsac_debet()).orElse(BigDecimal.ZERO).add(nominal)));
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
                        Double nominalWithTaxd = partner.getSubTotal()-(partner.getTax());
                        BigDecimal nominalWithTax = new BigDecimal(Double.toString(nominalWithTaxd));
                        BigDecimal saldoSenderPartner = userAcc.getUsac_debet();
                        BigDecimal totalSaldoSenderPartner = saldoSenderPartner.subtract(nominalWithTax);
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

                        handleUpdateDebetTransactionsUserAccount(listAcc, request.getToRekening(), nominalWithTax);

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
                BigDecimal nominalPremi = premiCredit.getSecrPremiDebet();
                BigDecimal saldoSenderPremi = userAcc.getUsac_debet();
                BigDecimal totalSaldoSenderPremi = saldoSenderPremi.subtract(nominalPremi);
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

                handleUpdateDebetTransactionsUserAccount(listAcc, request.getToRekening(), nominalPremi);

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
                        BigDecimal nominalSalary = employee.getBesaTotalSalary();
                        BigDecimal saldoSender = userAcc.getUsac_debet();
                        BigDecimal totalSaldoSender = saldoSender.subtract(nominalSalary);
                        LocalDate createdDateSalary = employee.getBesaCreatedDate();
                        String invoiceSalary = "SAL-" + dateFormatter(createdDateSalary);

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

                        handleUpdateDebetTransactionsUserAccount(listAcc, request.getToRekening(), nominalSalary);

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
    public PaymentTransactionsDtoResponse getById(String string) {
        return null;
    }

    @Override
    public List<PaymentTransactionsDtoResponse> getAll() {
        return null;
    }

    @Override
    public Boolean deleteById(String string) {
        return null;
    }
}
