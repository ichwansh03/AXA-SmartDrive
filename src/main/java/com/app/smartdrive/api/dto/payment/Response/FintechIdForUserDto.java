package com.app.smartdrive.api.dto.payment.Response;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FintechIdForUserDto {
    private EnumPaymentType enumPaymentType;
    private Long fint_entityid;
}