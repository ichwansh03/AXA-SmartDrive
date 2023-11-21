package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;

public interface UserPhoneRepo extends JpaRepository<UserPhone, UserPhoneId>{
  
}
