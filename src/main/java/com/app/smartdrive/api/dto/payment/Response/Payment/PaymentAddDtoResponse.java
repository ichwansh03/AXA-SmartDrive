package com.app.smartdrive.api.dto.payment.Response.Payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentAddDtoResponse {
    private String paymentName;
    private String paymentDesc;
}
