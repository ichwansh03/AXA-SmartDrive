package com.app.smartdrive.api.controllers.users;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.response.UserUserAccountResponseDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/userAccount")
public class UserUserAccountController {
  private final UserUserAccountService userAccountService;

  @PatchMapping("/{ucId}")
  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> updateUC(@PathVariable("id") Long id, @PathVariable Long ucId, @Valid @RequestBody UserUserAccountResponseDto userPost){
    UserAccounts userAccounts = userAccountService.updateUserAccounts(id, ucId, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(userAccounts);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> createUC(@PathVariable("id") Long id, @Valid @RequestBody UserUserAccountResponseDto userPost){
    UserAccounts userAccounts= userAccountService.addUserAccounts(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userAccounts);
  }

  @DeleteMapping("/{ucId}")
  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> deleteUC(@PathVariable("id")Long id, @PathVariable("ucId") Long accountId){
    userAccountService.deleteUserAccounts(id, accountId);
    return ResponseEntity.status(HttpStatus.OK).body("UserAccount has been deleted");
  }
}
