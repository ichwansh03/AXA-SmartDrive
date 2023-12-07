    package com.app.smartdrive.api.dto.payment.Response.Banks;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanksIdForUserDtoResponse {
    private EnumPaymentType enumPaymentType;
    private Long bank_entity_id;
}
