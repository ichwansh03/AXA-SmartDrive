package com.app.smartdrive.api.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.smartdrive.api.dto.user.CreateUserDto;
import com.app.smartdrive.api.dto.user.UserAddressDto;
import com.app.smartdrive.api.entities.users.UserAddress;
import com.app.smartdrive.api.services.users.UserAddressService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/address")
public class UserAddressController {
  private final UserAddressService userAddressService;

  @PatchMapping("/{addressId}")
  public ResponseEntity<?> updateUserAddress(@PathVariable("id") Long id,
      @PathVariable("addressId") Long addressId, @ModelAttribute UserAddressDto userPost) {

    UserAddress userAddress = userAddressService.updateUserAddress(id, addressId, userPost);
    return ResponseEntity.status(HttpStatus.OK).body(userAddress);
  }

  @PostMapping
  public ResponseEntity<?> createUserAddress(@PathVariable("id") Long id, UserAddressDto userPost){
    UserAddress userAddress = userAddressService.createUserAddress(id, userPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(userAddress);
  }

  @DeleteMapping("/{addressId}")
  public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id, @PathVariable("addressId") Long addressId){
    userAddressService.deleteAddressById(id, addressId);
    return ResponseEntity.status(HttpStatus.OK).body("Address has been deleted");
  }
}
