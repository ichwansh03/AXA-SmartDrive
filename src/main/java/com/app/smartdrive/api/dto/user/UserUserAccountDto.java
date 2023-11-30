package com.app.smartdrive.api.dto.user;


import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.dto.payment.Response.FintechDto;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUserAccountDto {
  private EnumPaymentType enumPaymentType;
  private BanksDto banks;
  private FintechDto fintech;
  private String usac_accountno;
}
