package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import com.app.smartdrive.api.dto.user.request.UserUserAccountDtoRequest;
import com.app.smartdrive.api.dto.user.response.UserUserAccountResponseDto;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.users.UserUserAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/userAccount")
@PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.getUserEntityId() == #id)")
public class UserUserAccountController {
  private final UserUserAccountService userAccountService;

  @PatchMapping("/{ucId}")
  public ResponseEntity<?> updateAccount(@PathVariable("id") Long id, @PathVariable Long ucId,
                                    @Valid @RequestBody UserUserAccountDtoRequest userPost){
    UserAccounts userAccounts = userAccountService.updateUserAccounts(id, ucId, userPost);
    return ResponseEntity.status(HttpStatus.OK)
            .body(TransactionMapper.mapEntityToDto(userAccounts, UserUserAccountResponseDto.class));
  }

  @PostMapping
  public ResponseEntity<?> addAccount(@PathVariable("id") Long id, @Valid @RequestBody UserUserAccountDtoRequest userPost){
    UserAccounts userAccounts= userAccountService.addUserAccounts(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userAccounts);
  }

  @DeleteMapping("/{ucId}")
  public ResponseEntity<?> deleteUC(@PathVariable("id")Long id, @PathVariable("ucId") Long accountId){
    userAccountService.deleteUserAccounts(id, accountId);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("UserAccount has been deleted"));
  }
}
