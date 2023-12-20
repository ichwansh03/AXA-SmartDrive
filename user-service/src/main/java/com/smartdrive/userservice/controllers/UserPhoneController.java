package com.smartdrive.userservice.controllers;


import com.smartdrive.userservice.dto.request.UserPhoneRequestDto;
import com.smartdrive.userservice.dto.response.UserPhoneDto;
import com.smartdrive.userservice.entities.UserPhone;
import com.smartdrive.userservice.services.UserPhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/phone")
public class UserPhoneController {
  private final UserPhoneService userPhoneService;

  @PatchMapping("/{phoneNumber}")
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> updateUserPhone(@PathVariable("id") Long id,
                                           @PathVariable("phoneNumber") String phoneNumber,
                                           @Valid @ModelAttribute UserPhoneRequestDto userPost) {
    UserPhone updatedPhone = userPhoneService.updateUserPhone(id, phoneNumber, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(updatedPhone);
  }

  @PostMapping
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> addUserPhone(@PathVariable("id") Long id, @Valid @RequestBody UserPhoneDto userPost){
    UserPhone userPhone = userPhoneService.addUserPhone(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userPhone);
  }

  @DeleteMapping("/{phoneNumber}")
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> deleteUserPhone(@PathVariable("id") Long id, @PathVariable("phoneNumber") String phoneNumber){
    userPhoneService.deleteUserPhone(id, phoneNumber);
    return ResponseEntity.status(HttpStatus.OK).body("UserPhone has been deleted");
  }
}
