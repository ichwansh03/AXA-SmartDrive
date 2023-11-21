package com.app.smartdrive.api.entities.users;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class UserRolesId implements Serializable{
  @Column(name = "usro_entityid", nullable = false)
  private Long usroEntityId;

  @Enumerated(EnumType.STRING)
  @Column(name = "usro_role_name", nullable = false)
  private EnumUsers.roleName usroRoleName;
}
