package com.app.smartdrive.api.entities.users;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneId implements Serializable{
  private Long usphEntityId;
  private String usphPhoneNumber;
}
