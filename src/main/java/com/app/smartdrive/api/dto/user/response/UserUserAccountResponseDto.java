package com.app.smartdrive.api.dto.user.response;




import com.app.smartdrive.api.dto.payment.Response.Payment.PaymentDtoResponse;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUserAccountResponseDto extends UserUserAccountDto {
  private PaymentDtoResponse paymentDtoResponseBanks;
  private PaymentDtoResponse paymentDtoResponseFintech;
}