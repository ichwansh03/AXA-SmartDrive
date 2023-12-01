    package com.app.smartdrive.api.dto.payment.Response;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanksIdForUserDto {
    private EnumPaymentType enumPaymentType;
    private Long bank_entity_id;
}
