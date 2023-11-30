package com.app.smartdrive.api.services.payment;

import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.entities.payment.UserAccounts;

public interface UserAccountsService {
    List<UserAccounts> findAllUserAccounts();
    UserAccounts addUserAcc(UserAccounts userAccounts);
    UserAccounts updateUserAcc(UserAccounts userAccounts);
    void deleteUserAccountsById(Long usac_id);
    Optional<UserAccounts> findByIdUserAcc(Long usac_id);
} 
   

