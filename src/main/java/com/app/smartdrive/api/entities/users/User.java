package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users", schema = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userBusinessEntity")
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @OneToOne
  @JoinColumn(name = "entityId")
  private BusinessEntity userBusinessEntity;

  @Column(name = "user_name", nullable = false, length = 15)
  private String userName;

  @Column(name = "user_password", length = 256)
  private String userPassword;

  @Column(name = "user_full_name", length = 85)
  private String userFullName;

  @Column(name = "User_email", length = 25, nullable = false)
  private String userEmail;

  @Column(name = "user_birth_place", length = 55)
  private String userBirthPlace;

  @Column(name =  "user_birth_date")
  private LocalDateTime userBirthDate;

  @Column(name = "user_national_id", length = 20, nullable = false)
  private String userNationalId;

  @Column(name = "user_npwp", length = 35)
  private String userNPWP;

  @Column(name = "user_photo", length = 255)
  private String userPhoto;

  @Column(name = "user_modified_date")
  private LocalDateTime userModifiedDate;
}
