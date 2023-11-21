package com.app.smartdrive.api.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.app.smartdrive.api.entities.users.UserPhone;

@Repository
public interface UserPhoneRepository extends JpaRepository<UserPhone, Long> {
    
}
