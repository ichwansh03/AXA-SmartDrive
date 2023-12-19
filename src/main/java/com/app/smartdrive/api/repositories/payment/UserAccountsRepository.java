package com.app.smartdrive.api.repositories.payment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.smartdrive.api.entities.payment.UserAccounts;

import jakarta.transaction.Transactional;

public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {

    @Transactional
    @Modifying(clearAutomatically =  true)
    @Query(value = "DELETE from payment.user_accounts WHERE usac_accountno=:usac_accountno", nativeQuery = true)
    int deleteUserAccountByNoRek(String usac_accountno);

    @Query(value = "SELECT * from payment.user_accounts WHERE usac_accountno=:usac_accountno", nativeQuery = true)
    UserAccounts findByAccounts(String usac_accountno);
    
    @Query(value = "SELECT * from payment.user_accounts WHERE usac_accountno=:usac_accountno", nativeQuery = true)
    Optional<UserAccounts>  findByNorek(String usac_accountno);
}   
