package com.app.smartdrive.api.dto.user.request;

import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUserAccountDtoRequest extends UserUserAccountDto {
  Long paymentId;
}
