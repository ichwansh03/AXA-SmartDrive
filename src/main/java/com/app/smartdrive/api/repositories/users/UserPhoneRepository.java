package com.app.smartdrive.api.repositories.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.entities.users.UserPhoneId;

public interface UserPhoneRepository extends JpaRepository<UserPhone, UserPhoneId>{
  
    UserPhone findByUserUserEntityId(Long userEntityId);

    @Query(value = "SELECT * FROM USERS.USER_PHONE WHERE USPH_PHONE_NUMBER = ?1 AND USPH_ENTITYID = ?2", nativeQuery = true)
    Optional<UserPhone> findByUsphPhoneNumberAndUserId(String usphPhoneNumber, Long id);

    @Query(value = "SELECT USPH_PHONE_NUMBER FROM USERS.USER_PHONE WHERE USPH_PHONE_NUMBER = ?1", nativeQuery = true)
    Optional<String> findPhoneNumber(String usphPhoneNumber);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE USERS.USER_PHONE SET USPH_PHONE_NUMBER =?1 WHERE USPH_PHONE_NUMBER =?2", nativeQuery = true) 
    int setPhoneNumber(String postPhone, String phoneNumber);


}
