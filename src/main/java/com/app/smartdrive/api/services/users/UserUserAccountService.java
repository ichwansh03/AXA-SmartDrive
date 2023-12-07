package com.app.smartdrive.api.services.users;

import java.util.List;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.dto.user.response.UserUserAccountResponseDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.User;

public interface UserUserAccountService {
  UserAccounts updateUserAccounts(Long id, Long accountid, UserUserAccountDto userPost);

  UserAccounts addUserAccounts(Long id, UserUserAccountDto userPost);

  List<UserAccounts> createUserAccounts(List<UserUserAccountDto> userPost, User user, Long paymentId);

  void deleteUserAccounts(Long id, Long ucId);
}
