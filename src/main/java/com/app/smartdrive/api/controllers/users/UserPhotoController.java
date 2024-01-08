package com.app.smartdrive.api.controllers.users;

import com.app.smartdrive.api.dto.auth.response.MessageResponse;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.users.FileStorageService;
import com.app.smartdrive.api.services.users.UserPhotoService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.implementation.PhotoUserStorageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@PreAuthorize("isAuthenticated() && (hasAuthority('Admin') || principal.user.getUserEntityId() == #id)")
@RequiredArgsConstructor
@CrossOrigin(allowCredentials = "true",allowedHeaders = {"Authorization", "Content-Type", "Origin"}, origins = "http://localhost:3006", maxAge = 2592000)
@RequestMapping("/user/{id}/photo")
public class UserPhotoController {
  
  private final UserPhotoService userPhotoService;
  private final UserService userService;
  private final Path pathPhoto = Paths.get("C:\\Izhar\\AXA-SmartDrive\\assets\\UserPhotos").toAbsolutePath().normalize();
  private final FileStorageService photoUserStorageServiceImpl = new PhotoUserStorageServiceImpl(pathPhoto);

  @PostMapping
  public ResponseEntity<?> savePhotoUser(@RequestParam MultipartFile photo, @PathVariable("id") Long id) throws Exception{
    String photoName = userPhotoService.savePhoto(photo, id);
    return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(photoName));
  }

  @GetMapping
  @ResponseBody
  public ResponseEntity<?> getImageDynamicType(@PathVariable("id") Long id, HttpServletRequest request) {
    User user = userService.getById(id);
    String photoName = user.getUserPhoto();
    Resource resource = photoUserStorageServiceImpl.loadFileAsResource(photoName);
    String contentType = null;

    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
  }
}
