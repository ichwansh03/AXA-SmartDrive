package com.smartdrive.userservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesId implements Serializable{
  
  public UserRolesId(Long usroEntityId) {
    this.usroEntityId = usroEntityId;
  }

  @Column(name = "usro_entityid", nullable = false)
  private Long usroEntityId;

  @Enumerated(EnumType.STRING)
  @Column(name = "usro_role_name", nullable = false)
  private EnumUsers.RoleName usroRoleName;
}
