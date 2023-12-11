package com.app.smartdrive.api.services.payment.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.SaldoIsNotEnoughException;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransactionsDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransaksiResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TypeTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPayment;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransaksiMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TopupFintechMapper;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TransferTransactionsMapper;
import com.app.smartdrive.api.repositories.payment.PaymentTransactionsRepository;
import com.app.smartdrive.api.repositories.payment.UserAccountsRepository;
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
    public TransaksiResponse transaksi(TransactionsDtoRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        TransaksiResponse dto = new TransaksiResponse();
        PaymentTransactions transactions = new PaymentTransactions();
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions transactions2 = new PaymentTransactions();
        
        int countt = 1;
        if(listPayment.isEmpty()){
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
                            countt++;
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
                    addaPY(transactions2);

                    userAccountsRepository.save(userAccounts);
                    dto = TransaksiMapper.mapperTransactionsDto(request);
                }
            }
        
        return dto;
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
