package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.master.Cities;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Data
@Entity
@Table(name = "user_address", schema = "users")
@NoArgsConstructor
public class UserAddress {

  @EmbeddedId
  UserAdressId userAdressId;

  @Column(name = "usdr_address1")
  private String usdrAddress1;

  @Column(name = "usdr_address2")
  private String usdrAdress2;
  
  @Column(name = "usdr_city_id")
  private Long usdrCityId;

  @Column(name = "usdr_modified_date")
  private LocalDateTime usdrModifiedDate;

  @ManyToOne
  @MapsId("usdrEntityId")
  @JoinColumn(name = "usdr_entityid")
  @JsonBackReference
  User user;

  @ManyToOne
  @MapsId("usdrCityId")
  @JoinColumn(name = "usdr_city_id")
  @JsonBackReference
  private Cities city;
}
