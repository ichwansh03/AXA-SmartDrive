package com.smartdrive.userservice.services.implementation;

import com.smartdrive.userservice.entities.User;
import com.smartdrive.userservice.repositories.UserRepository;
import com.smartdrive.userservice.services.UserPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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