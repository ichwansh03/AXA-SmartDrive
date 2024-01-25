package com.app.smartdrive.api.services.users.implementation;

import com.app.smartdrive.api.services.users.FileStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Qualifier("photoUserStorageServiceImpl")
public class PhotoUserStorageServiceImpl implements FileStorageService {
  private final Path fileStorageLocation;

  public PhotoUserStorageServiceImpl(Path path) {
//    fileStorageLocation = Paths.get("C:\\Izhar\\AXA-SmartDrive\\assets\\UserPhotos").toAbsolutePath().normalize();
    this.fileStorageLocation = path;
  }

  @Override
  public String storeFile(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if(fileName.contains("..")){
        throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
      }
      String uniqueFile = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
      Path targetLocation = fileStorageLocation.resolve(uniqueFile);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
      return uniqueFile;
    } catch (IOException e) {
      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
    }
  }

  @Override
  public Resource loadFileAsResource(String fileName) {
    try {
      Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists()) {
        return resource;
      } else {
        throw new RuntimeException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new RuntimeException("File not found " + fileName, ex);
    }
  }
}
