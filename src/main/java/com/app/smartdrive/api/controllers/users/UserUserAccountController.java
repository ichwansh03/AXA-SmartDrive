package com.app.smartdrive.api.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserUserAccountDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/userAccount")
public class UserUserAccountController {
  private final UserUserAccountService userAccountService;

  @PatchMapping("/{ucId}")
  public ResponseEntity<?> updateUC(@PathVariable("id") Long id, @PathVariable Long ucId, UserUserAccountDto userPost){
    UserAccounts userAccounts = userAccountService.updateUserAccounts(id, ucId, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(userAccounts);
  }

  @PostMapping
  public ResponseEntity<?> createUC(@PathVariable("id") Long id, UserUserAccountDto userPost){
    UserAccounts userAccounts= userAccountService.createUserAccounts(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userAccounts);
  }

  @DeleteMapping("/{ucId}")
  public ResponseEntity<?> deleteUC(@PathVariable("id")Long id, @PathVariable("ucId") Long ucId){
    userAccountService.deleteUserAccounts(id, ucId);
    return ResponseEntity.status(HttpStatus.OK).body("UserAccount has been deleted");
  }
}
