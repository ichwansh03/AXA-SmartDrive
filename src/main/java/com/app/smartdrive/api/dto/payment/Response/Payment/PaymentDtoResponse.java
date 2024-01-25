    package com.app.smartdrive.api.dto.payment.Response.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDtoResponse {
    private Long paymentEntityId;
    private String paymentName;
    private String paymentDesc;
}
