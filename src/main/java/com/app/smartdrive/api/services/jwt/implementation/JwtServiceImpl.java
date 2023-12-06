package com.app.smartdrive.api.services.jwt.implementation;

import com.app.smartdrive.api.services.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
  @Value("${token.signing.key}")
  private String jwtSigningKey;
  @Override
  public String extractUserName(String token) {
    return null;
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return null;
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    return false;
  }

  private <T> T extractClaim(String token, Function<Claims,T>)
}
