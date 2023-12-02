package com.app.smartdrive.api.dto.user.request;

import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUserAccountRequestDto extends UserUserAccountDto{
  Long bankId;
  Long fintechId;
}
