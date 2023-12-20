package com.smartdrive.userservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_phone", schema = "users")
public class UserPhone {

  @EmbeddedId
  private UserPhoneId userPhoneId;

  @Column(name = "usph_phone_type")
  private String usphPhoneType; //just HP or HOME
  
  @Column(name = "usph_mime")
  private String usphMime;

  @Column(name = "usph_status")
  private String usphStatus;

  @Column(name = "usph_modified_date")
  private LocalDateTime usphModifiedDate;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "usph_entityid")
  @MapsId("usphEntityId")
  @JsonBackReference
  private User user;
}
