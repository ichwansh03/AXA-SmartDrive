package com.app.smartdrive.api.services.users;

import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;

public interface UserUserAccountService {
  UserAccounts updateUserAccounts(Long id, Long accountid, CreateUserDto userPost);
}
