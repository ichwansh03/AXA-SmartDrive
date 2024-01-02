package com.app.smartdrive.api.dto.payment.Response.Fintech;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FintechDtoResponse {
    // private EnumPaymentType enumPaymentType;
    // private Long fint_entityid;
    private String fint_name;
    private String fint_desc;
}
