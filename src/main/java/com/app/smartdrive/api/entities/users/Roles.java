package com.app.smartdrive.api.entities.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles", schema = "users")
public class Roles {
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "role_name", length = 2)
  private EnumUsers.roleName roleName;

  @Column(name = "role_description", length = 35)
  private String roleDescription;

  @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserRoles> userRoles;
}
