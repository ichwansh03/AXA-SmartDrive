package com.app.smartdrive.api.repositories.auth;

import com.app.smartdrive.api.entities.auth.RefreshToken;
import com.app.smartdrive.api.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByRetoToken(String token);

  @Modifying
  int deleteByUser(User user);
}
