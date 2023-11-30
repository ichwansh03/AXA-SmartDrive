package com.app.smartdrive.api.entities.users;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdressId implements Serializable{
  private Long usdrId;
  private Long usdrEntityId;
}
