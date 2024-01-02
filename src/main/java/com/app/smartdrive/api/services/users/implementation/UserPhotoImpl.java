package com.app.smartdrive.api.services.users.implementation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
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
  private final UserRepository userRepository;
  
  @Override
  public String savePhoto(MultipartFile photo, Long userId) throws Exception {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
    String rootPath = new File("").getAbsolutePath();
    String path = "\\src\\main\\resources\\image\\userPhoto\\";
    Path pathPhotoUser = Paths.get(rootPath+path+user.getUserPhoto());
    Files.deleteIfExists(pathPhotoUser);
    String uniqueFile = UUID.randomUUID().toString()+"_"+photo.getOriginalFilename();
    user.setUserPhoto(uniqueFile);
    File file = new File(rootPath+path
            + uniqueFile);
    photo.transferTo(file);
    userRepository.save(user);
    return uniqueFile;
  }
}