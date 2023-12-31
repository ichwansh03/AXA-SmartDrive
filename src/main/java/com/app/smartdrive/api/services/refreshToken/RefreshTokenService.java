package com.app.smartdrive.api.services.refreshToken;

import com.app.smartdrive.api.Exceptions.TokenRefreshException;
import com.app.smartdrive.api.entities.auth.RefreshToken;
import com.app.smartdrive.api.repositories.auth.RefreshTokenRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  @Value("${jwt.refreshExpired}")
  private Long refreshTokenDurationMs;

  private final UserRepository userRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  private final JwtService jwtService;

  public Optional<RefreshToken> findByToken(String token){
    return refreshTokenRepository.findByRetoToken(token);
  }

  public RefreshToken createRefreshToken(Long userId){
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setRetoExpiryDate(Instant.now().plusSeconds(refreshTokenDurationMs));
    refreshToken.setRetoToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Transactional
  public RefreshToken verifyExpiration(RefreshToken token){
    if(token.getRetoExpiryDate().compareTo(Instant.now()) < 0){
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException("Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  public String generateJwtFromRefreshToken(String token){
    return findByToken(token)
            .map(this::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> jwtService.generateToken(user))
            .orElseThrow(() -> new TokenRefreshException("Refresh token is not in database"));
  }

  @Transactional
  public int deleteByUserId(Long userId){
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}
