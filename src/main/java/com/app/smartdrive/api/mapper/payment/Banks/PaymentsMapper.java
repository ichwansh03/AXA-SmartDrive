package com.app.smartdrive.api.mapper.payment.Banks;

import com.app.smartdrive.api.dto.payment.Request.Banks.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Banks.PaymentDtoResponse;
import com.app.smartdrive.api.entities.payment.Banks;

public class PaymentsMapper {
    public static PaymentDtoResponse convertEntityToDto(PaymentRequestsDto request){
        PaymentDtoResponse paymentDto = PaymentDtoResponse.builder()
            .payment_name(request.getPayment_name())
            .payment_desc(request.getPayment_desc())
            .build();
        return paymentDto;
    }
}
