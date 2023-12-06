package com.app.smartdrive.api.controllers.users;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.services.users.UserPhoneService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/phone")
public class UserPhoneController {
  private final UserPhoneService userPhoneService;

  @PatchMapping("/{phoneNumber}")
  public ResponseEntity<?> updateUserPhone(@PathVariable("id") Long id,
      @PathVariable("phoneNumber") String phoneNumber, @Valid @ModelAttribute UserPhoneRequestDto userPost) {
    UserPhone updatedPhone = userPhoneService.updateUserPhone(id, phoneNumber, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(updatedPhone);
  }

  @PostMapping
  public ResponseEntity<?> addUserPhone(@PathVariable("id") Long id, @Valid @RequestBody UserPhoneDto userPost){
    UserPhone userPhone = userPhoneService.addUserPhone(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userPhone);
  }

  @DeleteMapping("/{phoneNumber}")
  public ResponseEntity<?> deleteUserPhone(@PathVariable("id") Long id, @PathVariable("phoneNumber") String phoneNumber){
    userPhoneService.deleteUserPhone(id, phoneNumber);
    return ResponseEntity.status(HttpStatus.OK).body("UserPhone has been deleted");
  }
}
