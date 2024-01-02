package com.app.smartdrive.api.entities.users;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.service_order.Services;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @Column(name = "user_entityid")
  private Long userEntityId;

  @Column(name = "user_name", nullable = false, length = 15, unique = true)
  private String userName;

  @Column(name = "user_password", length = 256)
  private String userPassword;

  @Column(name = "user_full_name", length = 85)
  private String userFullName;

  @Column(name = "user_email", length = 25, nullable = false, unique = true)
  private String userEmail;

  @Column(name = "user_birth_place", length = 55)
  private String userBirthPlace;

  @Column(name =  "user_birth_date")
  private LocalDateTime userBirthDate;

  @Column(name = "user_national_id", length = 20, nullable = false, unique = true)
  private String userNationalId;

  @Column(name = "user_npwp", length = 35, unique = true)
  private String userNPWP;

  @Column(name = "user_photo", length = 255)
  private String userPhoto;

  @Column(name = "user_modified_date")
  private LocalDateTime userModifiedDate;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @MapsId
  @JoinColumn(name = "user_entityid")
  @JsonBackReference
  private BusinessEntity userBusinessEntity;
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JsonManagedReference
  @PrimaryKeyJoinColumn
  private List<UserPhone> userPhone;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserRoles> userRoles;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserAddress> userAddress;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonBackReference
  private Employees employees;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private List<UserAccounts> userAccounts;

  @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
  List<CustomerRequest> customerRequest;

  @JsonIgnore
  @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
  List<Services> services;
}
