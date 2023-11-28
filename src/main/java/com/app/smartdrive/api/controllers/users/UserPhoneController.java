package com.app.smartdrive.api.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.dto.user.CreateUserDto;
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
      @PathVariable("phoneNumber") String phoneNumber, @ModelAttribute CreateUserDto userPost) {
    UserPhone updatedPhone = userPhoneService.updateUserPhone(id, phoneNumber, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(updatedPhone);
  }
}
