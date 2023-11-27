package com.app.smartdrive.api.entities.users;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneId implements Serializable{
  @Column(name = "usph_entityid", nullable = false)
  private Long usphEntityId;

  @Column(name = "usph_phone_number", nullable = false, updatable = true)
  private String usphPhoneNumber;
}
