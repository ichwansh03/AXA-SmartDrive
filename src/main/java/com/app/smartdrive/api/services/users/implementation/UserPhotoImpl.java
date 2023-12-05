package com.app.smartdrive.api.services.users.implementation;

import java.io.File;
import org.springframework.stereotype.Service;
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
  public String addPhoto(MultipartFile photo, Long userId) throws Exception {
    User user = userRepository.findById(userId).get();
    user.setUserPhoto(photo.getOriginalFilename());
    String path = new File("").getAbsolutePath();
    photo.transferTo(new File(path+"\\src\\main\\resources\\image\\"
        + photo.getOriginalFilename()));
    userRepository.save(user);
    return photo.getOriginalFilename();
  }
}