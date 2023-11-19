package com.app.smartdrive.api.entities.users;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdressId implements Serializable{
  private Long usdrId;
  private Long usdrEntityId;
}
