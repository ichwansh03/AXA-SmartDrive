package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.payment.UserAccounts;
import com.app.smartdrive.api.entities.service_order.Services;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userBusinessEntity")
@Entity
@Data
@Table(name = "users", schema = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> listRole = this.userRoles.stream().map(role -> role.getUserRolesId().getUsroRoleName().getValue()).toList();
    return listRole.stream().map(SimpleGrantedAuthority::new).toList();
  }

  @Override
  public String getPassword() {
    return userPassword;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

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

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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

//  @JsonIgnore
  @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
  List<CustomerRequest> customerRequest;

  @JsonIgnore
  @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
  List<Services> services;
}
