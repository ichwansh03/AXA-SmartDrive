package com.app.smartdrive.api.services.jwt;

import com.app.smartdrive.api.entities.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Optional;

public class JwtUtils {

  @Value("${jwt.token.signing-key}")
  private String jwtSigningKey;
  private static final String SECRET_KEY = "F54D1DC2E823404A9D4CD0B6613839E1DB997175F26A66EB747A9573A4F72AD9";

  private static String COOKIE_NAME = "jwtcookie";
  private static String REFRESH_COOKIE = "refreshjwt";

  private static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
  private static final JWTVerifier JWT_VERIFIER = JWT.require(JWT_ALGORITHM).build();

  private static final int MAX_AGE_SECONDS = 60;
  private static final int MAX_REFRESH_WINDOW_SECONDS = 30;

  public static Cookie generateCookie(UserDetails userDetails){
    Instant now = Instant.now();
    String token = JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(now.plusSeconds(MAX_AGE_SECONDS))
            .withClaim("role", userDetails.getAuthorities().stream().toList())
            .withSubject(userDetails.getUsername())
            .sign(JWT_ALGORITHM);

    Cookie jwtCookie = new Cookie(COOKIE_NAME, token);
    jwtCookie.setPath("/api");
    jwtCookie.setHttpOnly(true);
    jwtCookie.setMaxAge(MAX_AGE_SECONDS);
    return jwtCookie;
  }

  public static Optional<String> getToken(HttpServletRequest request){
    Cookie[] cookies = request.getCookies();
    if (cookies == null) return Optional.empty();

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(COOKIE_NAME)) return Optional.of(cookie.getValue());
    }
    return Optional.empty();
  }

  public static Optional<DecodedJWT> getValidatedToken(String token){
    try{
      return Optional.of(JWT_VERIFIER.verify(token));
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }

  public static boolean isRefreshable(HttpServletRequest request){
    Optional<String> token = getToken(request);
    if(token.isEmpty()){
      return false;
    }
    Instant expiryTime =JWT.decode(token.get()).getExpiresAtAsInstant();
    Instant canBeRefreshedAfter = expiryTime.minusSeconds(MAX_REFRESH_WINDOW_SECONDS);
    return Instant.now().isAfter(canBeRefreshedAfter);
  }

  public static Optional<String> getJwtFromCookies(HttpServletRequest request, String cookieName){
    Cookie cookie = WebUtils.getCookie(request, cookieName);
    if (cookie != null) {
      return Optional.of(cookie.getValue());
    } else {
      return Optional.empty();
    }
  }

  public static String extractUserName(String token){
    return JWT.decode(token).getSubject();
  }

  public static String generateToken(User userDetails){
    Instant now = Instant.now();
    return JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(now.plusSeconds(MAX_AGE_SECONDS))
            .withClaim("role", userDetails.getUserRoles().stream().map(userRoles -> userRoles
                    .getUserRolesId()
                    .getUsroRoleName()
                    .getValue()).toList())
            .withSubject(userDetails.getUsername())
            .sign(JWT_ALGORITHM);
  }

  public static ResponseCookie generateJwtCookie(User userPrincipal){
    String jwt = generateToken(userPrincipal);
    ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
    return cookie;
  }

  public static ResponseCookie getCleanCookie(String cookieName, String path){
    ResponseCookie cookie = ResponseCookie.from(cookieName, null).path(path).httpOnly(true)
            .maxAge(0)
            .build();
    return cookie;
  }

  public static ResponseCookie generateCookie(String name, String value, String path){
    ResponseCookie cookie = ResponseCookie.from(name, value)
            .path(path)
            .maxAge(24*60*60)
            .httpOnly(true)
            .build();
    return cookie;
  }

  public static ResponseCookie generateRefreshJwtCookie(String refreshToken){
    return generateCookie(REFRESH_COOKIE, refreshToken, "/api/auth/refreshtoken");
  }
}
