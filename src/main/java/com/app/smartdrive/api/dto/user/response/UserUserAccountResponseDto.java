package com.app.smartdrive.api.dto.user.response;


import com.app.smartdrive.api.dto.payment.Response.BanksDto;
import com.app.smartdrive.api.dto.payment.Response.FintechDto;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUserAccountResponseDto extends UserUserAccountDto{
  private BanksDto banks;
  private FintechDto fintech;
}
