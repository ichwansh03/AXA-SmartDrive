package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.response.UserUserAccountResponseDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;

public interface UserUserAccountService {
  UserAccounts updateUserAccounts(Long id, Long accountid, UserUserAccountResponseDto userPost);
  UserAccounts createUserAccounts(Long id, UserUserAccountResponseDto userPost);
  void deleteUserAccounts(Long id, Long ucId);
}
