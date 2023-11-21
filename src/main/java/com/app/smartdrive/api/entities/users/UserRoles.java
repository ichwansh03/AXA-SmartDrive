package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  
}
