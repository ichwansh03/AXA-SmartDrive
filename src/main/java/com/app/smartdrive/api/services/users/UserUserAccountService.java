package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.response.UserUserAccountDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;

public interface UserUserAccountService {
  UserAccounts updateUserAccounts(Long id, Long accountid, UserUserAccountDto userPost);
  UserAccounts createUserAccounts(Long id, UserUserAccountDto userPost);
  void deleteUserAccounts(Long id, Long ucId);
}
