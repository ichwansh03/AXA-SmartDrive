package com.app.smartdrive.api.services.jwt;

import com.app.smartdrive.api.entities.users.EnumUsers;
import com.app.smartdrive.api.entities.users.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface JwtService {
  String extractUserName(String token);
  String generateToken(UserDetails userDetails);
  boolean isTokenValid(String token, UserDetails userDetails);

  boolean isTokenExpired(String token);

  String getJwtFromCookies(HttpServletRequest request, String cookieName);

  ResponseCookie generateJwtCookie(User userPrincipal);

  ResponseCookie getCleanJwtCookie(String cookieName, String path);

  ResponseCookie generateCookie(String name, String value, String path);

  boolean validateJwtToken(String authToken);

  ResponseCookie generateRefreshJwtCookie(String refreshToken);
}
