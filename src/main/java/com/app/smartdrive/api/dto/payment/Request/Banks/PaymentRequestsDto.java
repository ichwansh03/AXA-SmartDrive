package com.app.smartdrive.api.dto.payment.Request.Banks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestsDto {
    private String payment_name;
    private String payment_desc;
}
