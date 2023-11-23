package com.app.smartdrive.api.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.payment.UserAccounts;

public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {

    
} 
