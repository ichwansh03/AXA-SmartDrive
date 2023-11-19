package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
@Data
@NoArgsConstructor
@IdClass(UserPhoneId.class)
@Table(name = "user_phone", schema = "users")
public class UserPhone {

  @Id
  @Column(name = "usph_entityid")
  private Long usphEntityId;

  @Id
  @Column(name = "usph_phone_number")
  private String usphPhoneNumber;

  @Column(name = "usph_phone_type")
  private String usphPhoneType; //just HP or HOME
  
  @Column(name = "usph_mime")
  private String usphMime;

  @Column(name = "usph_status")
  private String usphStatus;

  @Column(name = "usph_modified_date")
  private LocalDateTime usphModifiedDate;

  @ManyToOne
  @MapsId("usphEntityId")
  @JoinColumn(name = "usph_entityid")
  @JsonBackReference
  private User user;
}
