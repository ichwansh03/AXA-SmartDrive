package com.app.smartdrive.api.services.refreshToken;

import com.app.smartdrive.api.Exceptions.TokenRefreshException;
import com.app.smartdrive.api.entities.auth.RefreshToken;
import com.app.smartdrive.api.repositories.auth.RefreshTokenRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

  @Value("${jwt.refreshExpired}")
  private Long refreshTokenDurationMs;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  public Optional<RefreshToken> findByToken(String token){
    return refreshTokenRepository.findByRetoToken(token);
  }

  public RefreshToken createRefreshToken(Long userId){
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setRetoExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setRetoToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Transactional
  public RefreshToken verifyExpiration(RefreshToken token){
    if(token.getRetoExpiryDate().compareTo(Instant.now()) < 0){
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getRetoToken(), "Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId){
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}
