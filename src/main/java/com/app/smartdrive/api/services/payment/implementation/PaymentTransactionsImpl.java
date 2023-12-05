package com.app.smartdrive.api.services.payment.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TopupFintechBankRequests;
import com.app.smartdrive.api.dto.payment.Request.PaymentTransactions.TransferTransactionsRequest;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactionsDto;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TopupBanksFintechResponse;
import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.TransferTransactionsResponse;
import com.app.smartdrive.api.entities.payment.PaymentTransactions;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPayment;
import com.app.smartdrive.api.mapper.payment.PaymentTransactions.TopupBanksFintechMapper;
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
    public TopupBanksFintechResponse topupBanksFintech(Long usac_id, TopupFintechBankRequests request) {
        List<UserAccounts> listAcc = userAccountsRepository.findAll();
        Optional<UserAccounts> idUA = userAccountsRepository.findById(usac_id);
        TopupBanksFintechResponse dto = new TopupBanksFintechResponse();
        PaymentTransactions transactions = new PaymentTransactions();
        List<PaymentTransactions> listPayment = repository.findAll();
        
       
        if(listPayment.isEmpty()){
            int con = 1;
            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + con );
            addaPY(transactions);
        }
        if(idUA.isPresent()){
            for (UserAccounts userAccounts : listAcc) {
                if(request.getUsac_accountno().equals(userAccounts.getUsac_accountno())){
                    UserAccounts userAcc = idUA.get();
                    int countt = 1;
                    for (PaymentTransactions py : listPayment) {
                        if(py.getPatrTrxnoRev()==null){
                            countt++;
                            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt ); 
                            transactions.setPatrTrxnoRev(py.getPatrTrxno());
                        }else{
                            countt++;
                            transactions.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countt );
                            transactions.setPatrTrxnoRev(py.getPatrTrxno());
                        }
                    }
                    transactions.setPatr_created_on(LocalDateTime.now());
                    transactions.setPatr_credit(request.getNominall());
                    transactions.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to());
                    transactions.setPatr_type(request.getEnumPayment());
                    transactions.setPatr_invoice_no(request.getPatr_invoice_no());
                    transactions.setPatr_notes(request.getPatr_notes());
                    addaPY(transactions);

                    userAcc.setUsac_debet(request.getNominall());
                    userAccountsRepository.save(userAcc);
                    dto = TopupBanksFintechMapper.mapperTransactionsDto(request);
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
        }
        
        
        else{
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
        Optional<UserAccounts> userId = userAccountsRepository.findById(usac_id);
        List<UserAccounts> listUA = userAccountsRepository.findAll();
        List<PaymentTransactions> listPayment = repository.findAll();
        PaymentTransactions payment = new PaymentTransactions();
        TransferTransactionsResponse dto = new TransferTransactionsResponse();
        UserAccounts userAccounts = userId.get();
        PaymentTransactions payment2 = new PaymentTransactions();
      
        int countTwo = 1;
        
        if(listPayment.isEmpty()){
            int contOne = 1;
            payment.setPatrTrxno("trx" + dateTimeFormatter() + "000" + contOne);
            addaPY(payment);
        }for (PaymentTransactions b : listPayment) {
            if(b.getPatrTrxnoRev()==null){
                countTwo++;
                payment.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countTwo ); 
                payment.setPatrTrxnoRev(b.getPatrTrxno());
            }else{
                countTwo++;
                payment.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countTwo );
                payment.setPatrTrxnoRev(b.getPatrTrxno());
            }
        }

        


        // if(userId.isPresent()){
        //     for (UserAccounts user : listUA) {
        //         if(request.getUsac_accountno().equals(user.getUsac_accountno())){ 
        //             Double temp = 0.0;
        //             payment.setPatr_created_on(LocalDateTime.now());

                   
                   

                    
        //             for (PaymentTransactions py : listPayment) {
                        
                        
                        
                        
                        
                        
                        
                        // if(py.getPatr_debet()==null && py.getPatr_credit()!=null) {
                        //     payment.setPatr_debet(request.getTransfer());
                        //     payment.setPatr_credit(null);
                        //     // userAccounts.setUsac_debet(request.);
                        //     payment.setPatr_usac_accountNo_from(user.getUsac_accountno());
                        //     payment.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to()); 
                        //     payment.setPatr_type(request.getEnumPayment());
                        //     payment.setPatr_invoice_no(request.getPatr_invoice_no());
                        //     payment.setPatr_notes(request.getPatr_notes());
                        //     addaPY(payment);
                        
                            

                            
                        //         if(py.getPatrTrxnoRev()==null){
                        //                 int countThree = countTwo;
                        //                 countThree++;
                        //                 payment2.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countThree ); 
                        //                 payment2.setPatrTrxnoRev(payment.getPatrTrxno());
                        //         }
                        //             Double usac_debet = user.getUsac_debet();
                        //              usac_debet = usac_debet - request.getTransfer();
                        //             payment2.setPatr_created_on(LocalDateTime.now());
                        //             payment2.setPatr_credit(usac_debet);
                        //             payment2.setPatr_debet(null);
                        //             payment2.setPatr_usac_accountNo_from(user.getUsac_accountno());
                        //             payment2.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to()); 
                        //             payment2.setPatr_type(request.getEnumPayment());
                        //             payment2.setPatr_invoice_no(request.getPatr_invoice_no());
                        //             payment2.setPatr_notes(request.getPatr_notes());
                        //             addaPY(payment2);
                                
                            
                            

                        // }else if(py.getPatr_debet() == null && py.getPatr_credit()!=null){
                        //     payment.setPatr_debet(request.getTransfer());
                        //     payment.setPatr_credit(null);
                        //     // userAccounts.setUsac_debet(request.);
                        //     payment.setPatr_usac_accountNo_from(user.getUsac_accountno());
                        //     payment.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to()); 
                        //     payment.setPatr_type(request.getEnumPayment());
                        //     payment.setPatr_invoice_no(request.getPatr_invoice_no());
                        //     payment.setPatr_notes(request.getPatr_notes());
                        //     addaPY(payment);
                        
                            

                            
                        //         if(py.getPatrTrxnoRev()==null){
                        //                 int countThree = countTwo;
                        //                 countThree++;
                        //                 payment2.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countThree ); 
                        //                 payment2.setPatrTrxnoRev(payment.getPatrTrxno());
                        //         }
                        // }
                        // userAccounts.setUsac_debet(payment2.getPatr_credit());
                        // }
                        
                        
                    // }
            
                // if(rev < 2)
                    
                    int countThree = countTwo;

                     countThree++;
                     
                     
                     payment.setPatr_credit(null);
                     payment.setPatr_debet(request.getTransfer());
                     payment.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to());
                     // payment.setPatr_usac_accountNo_from(user.getUsac_accountno());
                     
                     PaymentTransactions savedPayment = this.repository.saveAndFlush(payment);
                     
                     Double total = userAccounts.getUsac_debet();
                     total = total - request.getTransfer();
                     

                     payment2.setPatrTrxno("trx" + dateTimeFormatter() + "000" + countThree ); 
                   payment2.setPatr_credit(total);
                   payment2.setPatr_debet(null);
                   payment2.setPatr_usac_accountNo_to(request.getPatr_usac_accountNo_to());
                   payment2.setPatrTrxnoRev(savedPayment.getPatrTrxno());

                   userAccounts.setUsac_debet(total);
                    // payment2.setPatr_usac_accountNo_from(user.getUsac_accountno());

                    // userAccountsRepository.save(userAccounts);
                    // this.repository.saveAll(List.of(payment, payment2));
                    this.repository.save(payment2);
                    this.userAccountsRepository.save(userAccounts);
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
