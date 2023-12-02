package com.app.smartdrive.api.dto.user;

import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.Data;

@Data
public class UserUserAccountDto {
  private EnumPaymentType enumPaymentType;
  private String usac_accountno;
}
