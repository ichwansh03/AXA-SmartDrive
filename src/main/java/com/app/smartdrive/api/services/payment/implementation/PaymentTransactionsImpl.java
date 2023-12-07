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
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupBankRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupBanksResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPayment;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TopupBanksMapper;
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

    @Override
    public PaymentTransactionsDto addPaymentTransactions(PaymentTransactionsDto paymentTransactionsDto) {
        // PaymentTransactions pT = new PaymentTransactions();
        
        // pT.setPatrTrxno(paymentTransactionsDto.getPatrTrxno());
        // pT.setPatr_created_on(LocalDateTime.now());
        // pT.setPatr_debet(paymentTransactionsDto.getPatr_debet());
        // pT.setPatr_credit(paymentTransactionsDto.getPatr_credit());
        // pT.setPatr_usac_accountNo_from(paymentTransactionsDto.getPatr_usac_accounntNo_from());
        // pT.setPatr_usac_accountNo_to(paymentTransactionsDto.getPatr_usac_accountNo_to());
        // pT.setPatr_type(paymentTransactionsDto.getEnumPayment());
        // pT.setPatr_invoice_no(paymentTransactionsDto.getPatr_invoice_no());
        // pT.setPatr_notes(paymentTransactionsDto.getPatr_notes());
        // pT.setPatrTrxnoRev(paymentTransactionsDto.getPatrTrxnoRev());
        // repository.save(pT);

        // PaymentTransactionsDto dto = PaymentTransactionsMapper.convertEntityToDto(pT);
        return null;
    }

    public String dateTimeFormatter(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(formatter);
        return formattedDate;
    }
    

    

    @Override
    public TopupFintechResponse topupFintech(Long usac_id, TopupFintechRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        Optional<UserAccounts> idUA = userAccountsRepository.findById(usac_id);
        TopupFintechResponse dto = new TopupFintechResponse();
        PaymentTransactions transactions = new PaymentTransactions();
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions transactions2 = new PaymentTransactions();
        
        int countt = 1;
        if(listPayment.isEmpty()){
            int con = 1;
            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + con );
            addaPY(transactions);
        }
        if(idUA.isPresent()){
            for (UserAccounts userAccounts : listAcc) {
                if(request.getUsac_accountno().equals(userAccounts.getUsac_accountno())){
                    UserAccounts userAcc = idUA.get();
                    
                    for (PaymentTransactions py : listPayment) {
                        if(py.getPatrTrxnoRev()==null){
                            countt++;
                            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt ); 
                            transactions.setPatrTrxnoRev(py.getPatrTrxno());
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
                    
                    if(request.getPatr_usac_accountNo_to().equals(userAcc.getUsac_accountno())){
                        if(userAcc.getUsac_debet()==null){
                            userAcc.setUsac_debet(request.getNominall());
                        }else{
                            Double saldo = userAcc.getUsac_debet();
                            Double newSaldo = saldo + request.getNominall();
                            userAcc.setUsac_debet(newSaldo);
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


                    // userAcc.setUsac_debet(request.getNominall());
                    userAccountsRepository.save(userAcc);
                    dto = TopupFintechMapper.mapperTopupFintech(request);
                }
            
            }
        }
        return dto;
    }

    @Override
    public TopupBanksResponse topupBanks(Long usac_id, TopupBankRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        Optional<UserAccounts> idUA = userAccountsRepository.findById(usac_id);
        TopupBanksResponse dto = new TopupBanksResponse();
        PaymentTransactions transactions = new PaymentTransactions();
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions transactions2 = new PaymentTransactions();
        
       
        if(listPayment.isEmpty()){
            int con = 1;
            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + con );
            transactions.setPatrTrxnoRev(transactions.getPatrTrxno()); 
            addaPY(transactions);
        }
        int countt = 1;
        if(idUA.isPresent()){
            for (UserAccounts userAccounts : listAcc) {
                if(request.getUsac_accountno().equals(userAccounts.getUsac_accountno())){
                    UserAccounts userAcc = idUA.get();
                    for (PaymentTransactions py : listPayment) {
                        if(py.getPatrTrxnoRev()==null){
                            countt++;
                            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt ); 
                            transactions.setPatrTrxnoRev(transactions.getPatrTrxno());
                        }else{
                            countt++;
                            int newC = countt;
                            newC--;
                            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt );
                            transactions.setPatrTrxnoRev("trx" + dateTimeFormatter() + "000" + newC);
                        }
                    }
                    countt++;
                    transactions.setPatr_created_on(LocalDateTime.now());
                    transactions.setPatr_debet(request.getNominall());
                    transactions.setPatr_credit(null);
                    transactions.setPatr_usac_accountNo_from(request.getUsac_accountno());
                    transactions.setPatr_usac_accountNo_to(null);
                    
                    if(request.getPatr_usac_accountNo_to().equals(userAcc.getUsac_accountno())){
                        if(userAcc.getUsac_debet()==null){
                            userAcc.setUsac_debet(request.getNominall());
                        }else{
                            Double saldo = userAcc.getUsac_debet();
                            Double newSaldo = saldo + request.getNominall();
                            userAcc.setUsac_debet(newSaldo);
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
                    transactions2.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to());
                    transactions2.setPatr_type(request.getEnumPayment());
                    transactions2.setPatr_invoice_no(request.getPatr_invoice_no());
                    transactions2.setPatr_notes(request.getPatr_notes());
                    transactions2.setPatrTrxnoRev(transactions.getPatrTrxno());
                    addaPY(transactions2);
                    

                    userAccountsRepository.save(userAcc);
                    dto = TopupBanksMapper.mapperTransactionsDto(request);
                }
            
            }
        }
        return dto;
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
    public TransferTransactionsResponse transfer(Long usac_id,TransferTransactionsRequest request) {
        List<UserAccounts> listUA = userAccountsRepository.findAll();
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions payment = new PaymentTransactions();
        TransferTransactionsResponse dto = new TransferTransactionsResponse();
        
        UserAccounts userAccount = userAccountsRepository.findById(usac_id).orElseThrow(() -> new 
        EntityNotFoundException("User account with id " + usac_id + " Not Found"));
        
        PaymentTransactions payment2 = new PaymentTransactions();
      
        int countTwo = 1;
        
        if(listPayment.isEmpty()){
            int contOne = 1;
            payment.setPatrTrxno("trx" + dateTimeFormatter() + "000" + contOne);
            addaPY(payment);
        }else{
            for (PaymentTransactions b : listPayment) {
                if(b.getPatrTrxnoRev()==null){
                    countTwo++;
                    payment.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countTwo ); 
                    payment.setPatrTrxnoRev(payment2.getPatrTrxno());
                }else{
                    countTwo++;
                    int newC = countTwo;
                    newC--;
                    String generatedValue = "trx" + dateTimeFormatter() + "000" + countTwo;
                    payment.setPatrTrxno(generatedValue );
                    payment.setPatrTrxnoRev("trx" + dateTimeFormatter() + "000" + newC );
                    
                }
            }
        }
        countTwo++;
    
       
        payment.setPatr_credit(null);
        payment.setPatr_created_on(LocalDateTime.now());
        payment.setPatr_debet(request.getTransfer());
        payment.setPatr_usac_accountNo_from(userAccount.getUsac_accountno());
        payment.setPatr_type(request.getEnumPayment());
        payment.setPatr_invoice_no(request.getPatr_invoice_no());
        payment.setPatr_notes(request.getPatr_notes());
        
        PaymentTransactions savedPayment = this.repository.saveAndFlush(payment);
        Double total = userAccount.getUsac_debet();
        total = total - request.getTransfer();
                     
        payment2.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countTwo );
        payment2.setPatr_created_on(LocalDateTime.now()); 
        payment2.setPatr_credit(request.getTransfer());
        payment2.setPatr_debet(null);
        for (UserAccounts user : listUA) {
            if(request.getPatr_usac_accountNo_to().equals(user.getUsac_accountno())){
                payment2.setPatr_usac_accountNo_to(user.getUsac_accountno());
                user.setUsac_debet(user.getUsac_debet() + request.getTransfer());
            }
        }
        payment2.setPatr_type(request.getEnumPayment());
        payment2.setPatr_invoice_no(request.getPatr_invoice_no());
        payment2.setPatr_notes(request.getPatr_notes());
        payment2.setPatrTrxnoRev(savedPayment.getPatrTrxno());
        userAccount.setUsac_debet(total);
        this.repository.save(payment2);
        this.userAccountsRepository.save(userAccount);
        dto = TransferTransactionsMapper.convertDtoRequestToResponse(request);
                
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
