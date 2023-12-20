package com.smartdrive.userservice.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles", schema = "users")
public class Roles {
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "role_name", length = 2)
  private EnumUsers.RoleName roleName;

  @Column(name = "role_description", length = 35)
  private String roleDescription;

  @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserRoles> userRoles;
}
