package com.app.smartdrive.api.repositories.users;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.users.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query(
      value = "SELECT * FROM USERS.USERS U JOIN USERS.USER_PHONE P ON U.USER_ENTITYID = P.USPH_ENTITYID WHERE USER_NAME = ?1 OR USER_EMAIL = ?1 OR USPH_PHONE_NUMBER = ?1", nativeQuery = true)
  Optional<User> findUserByIden(String iden);
}
