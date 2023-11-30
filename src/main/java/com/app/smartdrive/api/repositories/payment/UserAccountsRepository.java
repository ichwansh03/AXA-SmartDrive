package com.app.smartdrive.api.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.smartdrive.api.entities.payment.UserAccounts;

import jakarta.transaction.Transactional;

public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {

    @Transactional
    @Modifying(clearAutomatically =  true)
    @Query(value = "DELETE from payment.user_accounts WHERE usac_id=:usac_id", nativeQuery = true)
    int deleteUserAccountById(Long usac_id);
}   
