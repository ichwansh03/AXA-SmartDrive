package com.smartdrive.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdressId implements Serializable{
  private Long usdrId;
  private Long usdrEntityId;
}
