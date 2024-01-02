package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.payment.Request.Banks.BanksDtoRequests;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;



public interface BasePaymentManagementService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    Boolean deleteById(ID id);
} 