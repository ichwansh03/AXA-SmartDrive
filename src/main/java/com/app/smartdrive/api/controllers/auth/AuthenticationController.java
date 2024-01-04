package com.app.smartdrive.api.controllers.auth;

import com.app.smartdrive.api.Exceptions.TokenRefreshException;
import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import com.app.smartdrive.api.dto.user.ProfileDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.PasswordRequestDto;
import com.app.smartdrive.api.entities.auth.RefreshToken;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.refreshToken.RefreshTokenService;
import com.app.smartdrive.api.services.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;
  private final JwtService jwtService;

  @Value("${jwt.refresh.cookie}")
  private String jwtRefreshCookie;

  @Value("${jwt.cookie.name}")
  private String jwtCookie;

  @PostMapping("/signin")
  public ResponseEntity<?> loginCustomer(@Valid @RequestBody SignInRequest login){
    User user = authenticationService.signinCustomer(login);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserEntityId());
    ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);
    ResponseCookie jwtRefreshCookie = jwtService.generateRefreshJwtCookie(refreshToken.getRetoToken());

    return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(new MessageResponse("User authenticated"));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUpCustomer(@Valid @RequestBody CreateUserDto request){
    authenticationService.signup(request);
    return ResponseEntity.status(HttpStatus.CREATED).
            body(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/createAdmin")
  public ResponseEntity<?> createAdmin(@RequestBody CreateUserDto profileRequestDto){
    User user = authenticationService.createAdmin(profileRequestDto);
    userService.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logout(){
    ResponseCookie cookie = jwtService.getCleanCookie(jwtCookie, "/api");
    ResponseCookie refreshCookie = jwtService.getCleanCookie(jwtRefreshCookie, "/api/auth/refreshtoken");
    return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(new MessageResponse("You've been sign out!"));
  }

  @PatchMapping("/{id}/changePassword")
  @PreAuthorize("principal.getUserEntityId() == #id && isAuthenticated()")
  public ResponseEntity<?> changePassword(@RequestBody PasswordRequestDto passwordRequestDto
          , @PathVariable("id") Long id){
    String message = authenticationService.changePassword(id, passwordRequestDto);
    MessageResponse messageResponse = new MessageResponse(message);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(messageResponse);
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshToken(HttpServletRequest request){
    Optional<String> refreshToken = jwtService.getJwtFromCookies(request, jwtRefreshCookie);
    if(refreshToken.isPresent()){
      return refreshTokenService.findByToken(refreshToken.get())
              .map(token -> refreshTokenService.verifyExpiration(token))
              .map(users -> users.getUser())
              .map(user -> {
                ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .body(new MessageResponse("Token is refreshed successfully!"));
              })
              .orElseThrow(() -> new TokenRefreshException("Refresh token is not in database"));
    }

    return ResponseEntity.badRequest().body(new MessageResponse("Refresh token is empty!"));
  }
}
