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
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransaksiMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.BatchPartnerInvoiceMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TopupFintechMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransferTransactionsMapper;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.repositories.partner.BatchPartnerInvoiceRepository;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionsRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
import com.app.smartdrive.api.services.partner.BatchPartnerInvoiceService;
import com.app.smartdrive.api.services.payment.PaymentTransactionsService;

import java.util.Calendar;
import java.util.Date;  
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentTransactionsImpl implements PaymentTransactionsService{
    private final EntityManager entityManager;
    private final PaymentTransactionsRepository repository;
    private final UserAccountsRepository userAccountsRepository;
    private final BatchEmployeeSalaryRepository employeeSalaryRepository;
    private final BatchPartnerInvoiceRepository partnerInvoiceRepository;
    private final BatchPartnerInvoiceService serviceInvoicePartner;

    public PaymentTransactions addaPY(PaymentTransactions paymentTransactions){
        entityManager.persist(paymentTransactions);
        return paymentTransactions;
    }

    @Override
    public List<PaymentTransactions> findAllPaymentTransactions() {
        return repository.findAll();
    }

    public String dateTimeFormatter(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(formatter);
        return formattedDate;
    }
    
    public void automationPaymentTransactionsId(){
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions payment = new PaymentTransactions();
        int countFirst = 1;
        if(listPayment.isEmpty()){
            payment.setPatrTrxno("trx" + dateTimeFormatter() + countFirst );
        }else{
            for (PaymentTransactions paymentData : listPayment) {
                if(paymentData.getPatrTrxnoRev()==null){
                    countFirst++;
                    payment.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countFirst);
                }else{
                    countFirst++;
                    payment.setPatrTrxno("trx" + dateTimeFormatter() +  "000" + countFirst);
                    payment.setPatrTrxnoRev(payment.getPatrTrxno());
                }
            }
        }
        addaPY(payment);
    }

    

    @Override
    public TransaksiResponse transaksiByUser(TransactionsDtoRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        List<PaymentTransactions> listPayment = repository.findAll();
        List<BatchEmployeeSalary> listEmployeSalary = employeeSalaryRepository.findAll();
        List<BatchPartnerInvoice> listPartnerInvoice = serviceInvoicePartner.getAllInvoiceNotPaid();
        TransaksiResponse dto = new TransaksiResponse();
        PaymentTransactions transactions = new PaymentTransactions();
        PaymentTransactions transactions2 = new PaymentTransactions();
        
        int countt = repository.countTrxno();
        if(request.getEnumPayment() == EnumPayment.TOPUP_BANK || 
        request.getEnumPayment() == EnumPayment.TRANSFER || 
        request.getEnumPayment() == EnumPayment.TOPUP_FINTECH )
        {
           
            if(listPayment.isEmpty()){
                countt+=1;
                transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt );
                addaPY(transactions);
            }
                for (UserAccounts userAccounts : listAcc) {
                    if(request.getUsac_accountno().equals(userAccounts.getUsac_accountno())){
                        for (PaymentTransactions py : listPayment) {
                            if(py.getPatrTrxnoRev()==null){
                                countt++;
                                transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt ); 
                            }else{
                                int newC = countt;
                                newC--;
                                transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt );
                                transactions.setPatrTrxnoRev("trx" + dateTimeFormatter() + "000" + newC );
                            }
                        }
                        countt++;

                        transactions.setPatr_created_on(LocalDateTime.now());
                        transactions.setPatr_debet(request.getNominall());
                        transactions.setPatr_credit(null);
                        transactions.setPatr_usac_accountNo_to(null);
                        transactions.setPatr_usac_accountNo_from(request.getUsac_accountno());
                        
                        Double saldoSender = userAccounts.getUsac_debet();
                        EnumPayment typeTransaksi = request.getEnumPayment();
                        if(saldoSender == null || saldoSender == 0.0){
                            throw new SaldoIsNotEnoughException("Saldo anda:" + " Rp."+ saldoSender + ", Failed: "+ typeTransaksi + ", Harap melakukan pengisian saldo!");
                        }else if(saldoSender < request.getNominall()){
                            throw new SaldoIsNotEnoughException("Saldo anda:" + " Rp."+ saldoSender + ", Failed: "+ typeTransaksi + ", Saldo anda kurangg!");
                        }else{
                            Double totalSaldoSender = saldoSender - request.getNominall();
                            userAccounts.setUsac_debet(totalSaldoSender);
                            for (UserAccounts user : listAcc) {
                                if(request.getPatr_usac_accountNo_to().equals(user.getUsac_accountno())){   
                                    Double saldoRecipient = user.getUsac_debet();
                                    if(saldoRecipient==null){
                                        user.setUsac_debet(request.getNominall());
                                    }else{
                                        Double totalSaldoRecipient = request.getNominall() + saldoRecipient;
                                        user.setUsac_debet(totalSaldoRecipient);
                                    }
                                
                                }        
                            }      
                        }
                    
                        transactions.setPatr_type(request.getEnumPayment());
                        transactions.setPatr_invoice_no(request.getPatr_invoice_no());
                        transactions.setPatr_notes(request.getPatr_notes());
                        addaPY(transactions);

                        transactions2.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt);
                        transactions2.setPatr_created_on(LocalDateTime.now());
                        transactions2.setPatr_debet(null);
                        transactions2.setPatr_credit(request.getNominall());
                        transactions2.setPatr_usac_accountNo_from(null);
                        transactions2.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to());
                        transactions2.setPatr_type(request.getEnumPayment());
                        transactions2.setPatr_invoice_no(request.getPatr_invoice_no());
                        transactions2.setPatr_notes(request.getPatr_notes());
                        transactions2.setPatrTrxnoRev(transactions.getPatrTrxno());
                        repository.saveAndFlush(transactions2);
                        
                        userAccountsRepository.save(userAccounts);
                    
                    }
                }
        }else{
            throw new EntityNotFoundException("Failed Transaksi, Harap Pilih Tipe Transaksi yang Benar");
        }
        dto = TransaksiMapper.mapperTransactionsDto(request);
        return dto;
    }

    
    
    
    @Override
    public GenerateTransferResponse generateTransaksi(GenerateTransactionsRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        List<PaymentTransactions> listPayment = repository.findAll();
        List<BatchEmployeeSalary> listEmployeSalary = employeeSalaryRepository.findAll();
        List<BatchPartnerInvoice> listPartnerInvoice = serviceInvoicePartner.getAllInvoiceNotPaid();
        TransaksiResponse dto = new TransaksiResponse();
        GenerateTransferResponse dtoGenerate = new GenerateTransferResponse();
        PaymentTransactions transactions = new PaymentTransactions();
        PaymentTransactions transactions2 = new PaymentTransactions();
        int countt = repository.countTrxno();

        if(request.getTipePayment() == EnumPayment.CLAIM_EVENT || request.getTipePayment() == EnumPayment.PAID_PARTNER || 
        request.getTipePayment() == EnumPayment.PREMI ||request.getTipePayment() == EnumPayment.SALARY )
        {
            if(listPayment.isEmpty()){
                countt+=1;
                transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt );
                addaPY(transactions);
            }

            for (UserAccounts userAccounts : listAcc) {
                    if(request.getNoRekening().equals(userAccounts.getUsac_accountno())){
                        for (PaymentTransactions py : listPayment) {
                            if(py.getPatrTrxnoRev()==null){
                                countt++;
                                transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt ); 
                            }else{
                                int newC = countt;
                                newC--;
                                transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt );
                                transactions.setPatrTrxnoRev("trx" + dateTimeFormatter() + "000" + newC );
                            }
                        }
                        countt++;

                        transactions.setPatr_created_on(LocalDateTime.now());
                        transactions.setPatr_credit(null);
                        transactions.setPatr_usac_accountNo_to(null);
                        transactions.setPatr_usac_accountNo_from(request.getNoRekening());
                        transactions.setPatr_type(request.getTipePayment());
                        transactions.setPatr_invoice_no(null);//
                        transactions.setPatr_notes(request.getNotes());
                        repository.saveAndFlush(transactions);
                        
                        transactions2.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt);
                        transactions2.setPatr_created_on(LocalDateTime.now());
                        transactions2.setPatr_debet(null);
                        transactions2.setPatr_usac_accountNo_from(null);
                        transactions2.setPatr_usac_accountNo_to(request.getToRekening());
                        transactions2.setPatr_type(request.getTipePayment());
                        transactions2.setPatr_notes(request.getNotes());
                        transactions2.setPatrTrxnoRev(transactions.getPatrTrxno());
                        repository.saveAndFlush(transactions2);
                        
                        if(request.getTipePayment()== EnumPayment.SALARY){
                            for (BatchEmployeeSalary salary : listEmployeSalary) {
                                if(request.getToRekening().equals(salary.getBesaAccountNumber())){
                                    salary.setEmsTrasferDate(LocalDateTime.now());
                                    salary.setBesaPatrTrxno(transactions2.getPatrTrxnoRev());
                                    salary.setBesaPaidDate(LocalDateTime.now());
                                    salary.setBesaModifiedDate(LocalDateTime.now());
                                    employeeSalaryRepository.save(salary);
                                    
                                }
                            }
                        }else if(request.getTipePayment().equals(EnumPayment.PAID_PARTNER)){ 
                            for (BatchPartnerInvoice partner : listPartnerInvoice) {
                                if(request.getToRekening().equals(partner.getAccountNo())){
                                    Double nominalWithTax = partner.getSubTotal() - partner.getTax();
                                    
                                    transactions.setPatr_debet(nominalWithTax);
                                    partner.setPaidDate(LocalDateTime.now());
                                    partner.setStatus(BpinStatus.PAID);
                                    partner.setPaymentTransactions(transactions2);
                                    partner.setCreatedOn(LocalDateTime.now());
                                    transactions2.setPatr_invoice_no(partner.getNo());
                                    transactions2.setPatr_credit(nominalWithTax);
                                    repository.save(transactions);
                                    repository.save(transactions2);
                                    for (UserAccounts acc : listAcc) {
                                        if(partner.getAccountNo().equals(acc.getUsac_accountno())){
                                            if(acc.getUsac_debet() == null || acc.getUsac_debet() == 0.0){
                                                acc.setUsac_debet(nominalWithTax);
                                            }else{
                                                Double total = acc.getUsac_debet() + nominalWithTax;
                                                acc.setUsac_debet(total);
                                            }
                                           
                                            userAccountsRepository.save(acc);
                                        }
                                        
                                    }
                                    partnerInvoiceRepository.saveAndFlush(partner);
                                    dtoGenerate.setAccountNo(partner.getAccountNo());
                                    dtoGenerate.setInvoiceNo(partner.getNo());
                                    dtoGenerate.setPaidDate(partner.getPaidDate());
                                    dtoGenerate.setNominal(nominalWithTax);
                                    dtoGenerate.setStatus(partner.getStatus());
                                    dtoGenerate.setTrxNo(transactions2.getPatrTrxno());
                                }   
                            }
                        }else{
                            throw new TypeTransaksiNotFoundException("Harap memilih tipe transaksi yang benar!");
                        }
                        userAccountsRepository.save(userAccounts);
                    }
                }

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
