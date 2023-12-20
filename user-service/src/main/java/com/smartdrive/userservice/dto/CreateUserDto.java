package com.smartdrive.userservice.dto;

import lombok.Data;

@Data
public class CreateUserDto {
  private String userName;
  private String userPassword;
  private String fullName;
  private String email;
  private String birthPlace;
  private String userBirthDate;
  private String userNationalId;
  private String userNpwp;
  private String userPhoneNumber; //Buat dto spesifik dan photo dilainkan endpointnya
  private String address1;
  private String address2;
  private String city;
  private String accountType;
  private String bank;
  private String fintech;
  private String accNumber;
  private String userPhoto;
  private String phoneType;
  // private MultipartFile photo; //terpisah
}
