package com.app.smartdrive.api.services.users;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.dto.user.request.UserUserAccountDtoRequest;
import com.app.smartdrive.api.dto.user.response.UserUserAccountResponseDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.users.User;

public interface UserUserAccountService {

  UserAccounts updateUserAccounts(Long id, Long accountId, UserUserAccountDtoRequest userPost);

  List<UserAccounts> createUserAccounts(List<UserUserAccountDto> userPost, User user, Long paymentId);

  UserAccounts addUserAccounts(Long id, UserUserAccountDtoRequest userPost);

  void deleteUserAccounts(Long id, Long ucId);

  Optional<UserAccounts> getOptionalByUserAccountNo(String number);
}
