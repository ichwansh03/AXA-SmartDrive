package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.response.UserAddressDto;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.services.users.UserAddressService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/address")
public class UserAddressController {
  private final UserAddressService userAddressService;

  @PostMapping
  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> createUserAddress(@PathVariable("id") Long id, @Valid @RequestBody UserAddressRequestDto userPost){
    UserAddress userAddress = userAddressService.addUserAddress(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userAddress);
  }

  @PatchMapping("/{addressId}")
  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> updateUserAddress(@PathVariable("id") Long id,
      @PathVariable("addressId") Long addressId, @Valid @RequestBody UserAddressRequestDto userPost) {

    UserAddress userAddress = userAddressService.updateUserAddress(id, addressId, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(userAddress);
  }

  @DeleteMapping("/{addressId}")
  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id, @PathVariable("addressId") Long addressId){
    userAddressService.deleteAddressById(id, addressId);
    return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Address has been deleted"));
  }
}
