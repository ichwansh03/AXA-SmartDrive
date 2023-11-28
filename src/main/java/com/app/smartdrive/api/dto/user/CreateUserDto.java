package com.app.smartdrive.api.dto.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateUserDto {
  private String userName;
  private String userPassword;
  private String fullName;
  private String email;
  private String birthPlace;
  private LocalDateTime userBirthDate;
  private String userNationalId;
  private String userNpwp;
  private String userPhoneNumber;
  private String address1;
  private String address2;
  private String city;
  private String accountType;
  private String bank;
  private String fintech;
  private String accNumber;
  private String userPhoto;
}
