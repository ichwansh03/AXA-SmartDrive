package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.entities.users.UserPhone;
import com.app.smartdrive.api.services.users.UserPhotoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.user.getUserEntityId() == #id)")
@RequiredArgsConstructor
@RequestMapping("/user/{id}/photo")
public class UserPhotoController {
  
  private final UserPhotoService userPhotoService;

  @PostMapping
  public ResponseEntity<?> savePhotoUser(@RequestParam MultipartFile photo, @PathVariable("id") Long id) throws Exception{
    userPhotoService.savePhoto(photo, id);
    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Success save photo user"));
  }
}
