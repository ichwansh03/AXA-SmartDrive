package com.app.smartdrive.api.entities.users;

import com.app.smartdrive.api.entities.master.Cities;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Data
@Entity
@Table(name = "user_address", schema = "users")
@NoArgsConstructor
@IdClass(UserAdressId.class)
public class UserAddress {
  @Id
  @Column(name = "usdr_id")
  private Long usdrId;

  @Id
  @Column(name = "usdr_entityid")
  private Long usdrEntityId;

  @Column(name = "usdr_address1")
  private String usdrAddress1;

  @Column(name = "usdr_address2")
  private String usdrAdress2;
  
  @Column(name = "usdr_city_id")
  private Long usdrCityId;

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
