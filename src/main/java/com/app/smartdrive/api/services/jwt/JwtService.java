package com.app.smartdrive.api.services.jwt;

import com.app.smartdrive.api.entities.users.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUserName(String token);
  String generateToken(UserDetails userDetails);
  boolean isTokenValid(String token, UserDetails userDetails);

  String getJwtFromCookies(HttpServletRequest request);

  ResponseCookie generateJwtCookie(User userPrincipal);

  ResponseCookie getCleanJwtCookie();

  ResponseCookie generateRefreshJwtCookie(String refreshToken);
}
