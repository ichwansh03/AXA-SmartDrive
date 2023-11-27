package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
