package com.app.smartdrive.api.services.payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Request.Banks.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Request.Fintech.FintechDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechIdForUserDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;


public interface PaymentService extends BasePaymentManagementService<PaymentDtoResponse, Long>{
    PaymentDtoResponse addPayment(PaymentRequestsDto requests);
    PaymentDtoResponse updateById(Long id, PaymentRequestsDto requests);


    default boolean checkBusinesEntity(Long id, BusinessEntityRepository repository){
        List<BusinessEntity> businesData = repository.findAll();
        for (BusinessEntity bisnis : businesData) {
            if(id.equals(bisnis.getEntityId())){
                bisnis.setEntityModifiedDate(LocalDateTime.now());
                repository.save(bisnis);
                return true;
            }
        }
        return false;
    }
} 
