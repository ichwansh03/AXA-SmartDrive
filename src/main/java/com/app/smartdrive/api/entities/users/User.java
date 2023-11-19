package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;
import java.util.List;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.payment.User_accounts;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userBusinessEntity")
@Entity
@Data
@Table(name = "users", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @Column(name = "user_entityid")
  private Long userEntityId;

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

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_entityid")
  @JsonBackReference
  private BusinessEntity userBusinessEntity;
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @JsonManagedReference
  @PrimaryKeyJoinColumn
  private List<UserPhone> userPhone;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserRoles> userRoles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserAddress> userAddress;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private Employees employees;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<User_accounts> user_accounts;

  @OneToMany(mappedBy = "customer")
  List<CustomerRequest> customerRequest;
}
