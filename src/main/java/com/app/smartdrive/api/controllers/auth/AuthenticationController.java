package com.app.smartdrive.api.controllers.auth;

import com.app.smartdrive.api.Exceptions.TokenRefreshException;
import com.app.smartdrive.api.dto.auth.request.SignInRequest;
import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import com.app.smartdrive.api.dto.user.ProfileDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final JwtService jwtService;
  private final RefreshTokenService refreshTokenService;
  private final UserService userService;

  @PostMapping("/signin")
  public ResponseEntity<?> loginCustomer(@Valid @RequestBody SignInRequest login){
    User user = authenticationService.signin(login);
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserEntityId());
    ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);
    ResponseCookie jwtRefreshCookie = jwtService.generateRefreshJwtCookie(refreshToken.getRetoToken());

    ProfileDto userResponse = TransactionMapper.mapEntityToDto(user, ProfileDto.class);

    String jwt = jwtService.generateToken(user);

    userResponse.setRoles(user.getAuthorities());
    userResponse.setAccessToken(jwt);
    userResponse.setTokenType("Bearer");

    return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body(userResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUpCustomer(@Valid @RequestBody CreateUserDto request){
    authenticationService.signup(request);
    return ResponseEntity.status(HttpStatus.CREATED).
            body(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logout(){
    ResponseCookie cookie = jwtService.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been sign out!"));
  }

  @PostMapping("/refreshtoken")
  public ResponseEntity<?> refreshToken(HttpServletRequest request){
    String refreshToken = jwtService.getJwtFromCookies(request);
    if(StringUtils.hasLength(refreshToken)){
      return refreshTokenService.findByToken(refreshToken)
              .map(token -> refreshTokenService.verifyExpiration(token))
              .map(users -> users.getUser())
              .map(user -> {
                ResponseCookie jwtCookie = jwtService.generateJwtCookie(user);
                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .body(new MessageResponse("Token is refreshed successfully!"));
              })
              .orElseThrow(() -> new TokenRefreshException(refreshToken,
                      "Refresh token is not in database"));
    }

    return ResponseEntity.badRequest().body(new MessageResponse("Refresh token is empty!"));
  }
}
