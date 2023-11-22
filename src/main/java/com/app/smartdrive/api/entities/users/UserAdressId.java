package com.app.smartdrive.api.entities.users;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserAdressId implements Serializable{
  @Column(name = "usdr_id", nullable = false)
  private Long usdrId;

  @Column(name = "usdr_entityid", nullable = false)
  private Long usdrEntityId;
}
