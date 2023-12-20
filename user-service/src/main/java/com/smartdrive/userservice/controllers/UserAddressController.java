package com.smartdrive.userservice.controllers;


import com.smartdrive.userservice.dto.request.UserAddressRequestDto;
import com.smartdrive.userservice.entities.UserAddress;
import com.smartdrive.userservice.services.UserAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/address")
public class UserAddressController {
  private final UserAddressService userAddressService;

  @PostMapping
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> createUserAddress(
          @PathVariable("id") Long id, @Valid @RequestBody UserAddressRequestDto userPost){
    UserAddress userAddress = userAddressService.addUserAddress(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userAddress);
  }

  @PatchMapping("/{addressId}")
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> updateUserAddress(@PathVariable("id") Long id,
      @PathVariable("addressId") Long addressId, @Valid @RequestBody UserAddressRequestDto userPost) {

    UserAddress userAddress = userAddressService.updateUserAddress(id, addressId, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(userAddress);
  }

  @DeleteMapping("/{addressId}")
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id, @PathVariable("addressId") Long addressId){
    userAddressService.deleteAddressById(id, addressId);
    return ResponseEntity.status(HttpStatus.OK).body("Address has been deleted");
  }
}
