package com.app.smartdrive.api.dto.user.response;



import com.app.smartdrive.api.dto.payment.Response.Banks.BanksDtoResponse;
import com.app.smartdrive.api.dto.payment.Response.Fintech.FintechDtoResponse;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.entities.payment.Enumerated.EnumClassPayment.EnumPaymentType;
import com.app.smartdrive.api.mapper.GSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUserAccountResponseDto extends UserUserAccountDto{
  private BanksDtoResponse banks;
  private FintechDtoResponse fintech;

  @Override
  public String toString() {
    return GSON.toJson(this);
  }
}
