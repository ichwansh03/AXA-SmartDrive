package com.app.smartdrive.api.services.jwt.implementation;

import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
  @Value("${jwt.token.signing-key}")
  private String jwtSigningKey;
  @Value("${jwt.cookie.name}")
  private String jwtCookie;
  @Value("${jwt.refreshExpired}")
  private String jwtRefreshCookie;
  @Value("${jwt.expired}")
  private Long jwtExpirationMs;

  @Override
  public String extractUserName(String token) {
    return extractClaim(token, claims -> claims.getSubject());
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token,Function<Claims, T> claimsResolvers){
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
    return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
  }

  private boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token){
    return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
            .getBody();
  }

  private Key getSigningKey(){
    byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public String getJwtFromCookies(HttpServletRequest request){
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  @Override
  public ResponseCookie generateJwtCookie(User userPrincipal){
    String jwt = generateToken(userPrincipal);
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
    return cookie;
  }

  @Override
  public ResponseCookie getCleanJwtCookie(){
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
    return cookie;
  }

  public ResponseCookie generateCookie(String name, String value, String path){
    ResponseCookie cookie = ResponseCookie.from(name, value).path(path).maxAge(24*60*60).httpOnly(true).build();
    return cookie;
  }

  public ResponseCookie generateRefreshJwtCookie(String refreshToken){
    return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken");
  }

//  public ResponseCookie generateCookie(String name, String value)
//  private String generateTokenFromUsername(String username) {
//  }
}
