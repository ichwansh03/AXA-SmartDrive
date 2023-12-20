package com.smartdrive.userservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles", schema = "users")
@Data
@NoArgsConstructor
public class UserRoles {
  @EmbeddedId
  UserRolesId userRolesId;

  @Column(name = "usro_status")
  private String usroStatus; //ACTIVE OR INACTIVE

  @Column(name = "usro_modified_date")
  private LocalDateTime usroModifiedDate;

  @ManyToOne
  @MapsId("usroEntityId")
  @JoinColumn(name = "usro_entityid")
  @JsonBackReference
  private User user;

  @ManyToOne
  @MapsId("usroRoleName")
  @JoinColumn(name = "usro_role_name")
  @JsonBackReference
  private Roles roles;
}
