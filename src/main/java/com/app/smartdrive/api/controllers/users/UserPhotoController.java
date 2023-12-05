package com.app.smartdrive.api.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/photo")
public class UserPhotoController {
  
  private final UserPhotoService userPhotoService;

  @PostMapping
  public ResponseEntity<?> addUserPhone(@RequestParam MultipartFile photo, @PathVariable("userId") Long userId)throws Exception{
    String userphoto = userPhotoService.addPhoto(photo,userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(userphoto);
  }
}
