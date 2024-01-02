package com.app.smartdrive.api.dto.payment.Response.Banks;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BanksDtoResponse  {
    private String bank_name;
    private String bank_desc;
}
