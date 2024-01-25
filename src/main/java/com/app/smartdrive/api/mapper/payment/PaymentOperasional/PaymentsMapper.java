package com.app.smartdrive.api.mapper.payment.PaymentOperasional;

import com.app.smartdrive.api.dto.payment.Request.PaymentOperasional.PaymentRequestsDto;
import com.app.smartdrive.api.dto.payment.Response.Payment.PaymentDtoResponse;

public class PaymentsMapper {
    public static PaymentDtoResponse convertEntityToDto(PaymentRequestsDto request){
        PaymentDtoResponse paymentDto = PaymentDtoResponse.builder()
            .paymentName(request.getPaymentName())
            .paymentDesc(request.getPaymentDesc())
            .build();
        return paymentDto;
    }
}
