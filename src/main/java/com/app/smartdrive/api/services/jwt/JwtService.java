package com.app.smartdrive.api.services.jwt;

import com.app.smartdrive.api.entities.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.util.Optional;

@Service
public class JwtService {

  @Value("${jwt.token.signing-key}")
  private String jwtSigningKey;
  @Value("${jwt.cookie.name}")
  private String COOKIE_NAME;

  @Value("${jwt.refresh.cookie}")
  private String REFRESH_COOKIE;

  private Algorithm JWT_ALGORITHM;
  private JWTVerifier JWT_VERIFIER;
  @Value("${jwt.expired}")
  private int MAX_AGE_SECONDS;

  @Value("${jwt.refreshExpired}")
  private int MAX_AGE_REFRESH;

  @PostConstruct
  public void init() {
    this.JWT_ALGORITHM = Algorithm.HMAC256(jwtSigningKey);
    this.JWT_VERIFIER = JWT.require(JWT_ALGORITHM).build();
  }

  public Optional<DecodedJWT> getValidatedToken(String token){
    try{
      return Optional.of(JWT_VERIFIER.verify(token));
    } catch (JWTVerificationException e) {
      return Optional.empty();
    }
  }

  public Optional<String> getJwtFromCookies(HttpServletRequest request, String cookieName){
    Cookie cookie = WebUtils.getCookie(request, cookieName);
    if (cookie != null) {
      return Optional.of(cookie.getValue());
    } else {
      return Optional.empty();
    }
  }

  public String extractUserName(String token){
    return JWT.decode(token).getSubject();
  }

  public String generateToken(User userDetails){
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

  public ResponseCookie generateJwtCookie(User userPrincipal){
    String jwt = generateToken(userPrincipal);
    ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
    return cookie;
  }

  public ResponseCookie getCleanCookie(String cookieName, String path){
    ResponseCookie cookie = ResponseCookie.from(cookieName, null).path(path).httpOnly(true)
            .maxAge(0)
            .build();
    return cookie;
  }

  public ResponseCookie generateCookie(String name, String value, String path){
    ResponseCookie cookie = ResponseCookie.from(name, value)
            .path(path)
            .maxAge(MAX_AGE_REFRESH)
            .httpOnly(true)
            .build();
    return cookie;
  }

  public ResponseCookie generateRefreshJwtCookie(String refreshToken){
    return generateCookie(REFRESH_COOKIE, refreshToken, "/api/auth/refreshtoken");
  }
}
