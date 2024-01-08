package com.app.smartdrive.api.services.users.implementation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.services.users.FileStorageService;
import com.app.smartdrive.api.services.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.users.UserPhotoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPhotoImpl implements UserPhotoService {
  private final UserService userService;
  private final Path pathPhoto = Paths.get("C:\\Izhar\\AXA-SmartDrive\\assets\\UserPhotos").toAbsolutePath().normalize();
  private final FileStorageService photoUserStorageServiceImpl = new PhotoUserStorageServiceImpl(pathPhoto);

  @Override
  public String savePhoto(MultipartFile photo, Long userId) throws Exception {

    User user = userService.getById(userId);
    Files.deleteIfExists(pathPhoto.resolve(user.getUserPhoto()));
    String fileName = photoUserStorageServiceImpl.storeFile(photo);
    user.setUserPhoto(fileName);
    userService.save(user);
    return fileName;
  }
}