package com.app.smartdrive.api.services.users;

import org.springframework.web.multipart.MultipartFile;

public interface UserPhotoService {
  String savePhoto(MultipartFile photo, Long userId) throws Exception;
}
