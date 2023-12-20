package com.smartdrive.userservice.controllers;

import com.smartdrive.userservice.services.UserPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{id}/photo")
public class UserPhotoController {
  
  private final UserPhotoService userPhotoService;

  @PostMapping
//  @PreAuthorize("hasAuthority('Admin') || principal.getUserEntityId() == #id")
  public ResponseEntity<?> addUserPhone(@RequestParam MultipartFile photo, @PathVariable("id") Long id) throws Exception{
    String userphoto = userPhotoService.addPhoto(photo,id);
    return ResponseEntity.status(HttpStatus.CREATED).body(userphoto);
  }
}
