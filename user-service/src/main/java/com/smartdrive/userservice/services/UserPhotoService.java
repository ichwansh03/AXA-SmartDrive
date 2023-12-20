package com.smartdrive.userservice.services;

import org.springframework.web.multipart.MultipartFile;

public interface UserPhotoService {
  String addPhoto(MultipartFile photo, Long userId) throws Exception;
}
