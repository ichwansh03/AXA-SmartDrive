package com.smartdrive.userservice.entities;

//import com.app.smartdrive.api.entities.master.Cities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_address", schema = "users")
@IdClass(UserAdressId.class)
@NoArgsConstructor
public class UserAddress {

  @Id
  @Column(name = "usdr_id", nullable = false)
  @SequenceGenerator(
    name = "id-generator-address",
    sequenceName = "user_address_seq",
    allocationSize = 1,
    schema = "users"
  )
  @GeneratedValue(strategy = GenerationType.TABLE, 
    generator = "id-generator-address")
  private Long usdrId;

  @Id
  @Column(name = "usdr_entityid", nullable = false)
  private Long usdrEntityId;

  @Column(name = "usdr_address1")
  private String usdrAddress1;

  @Column(name = "usdr_address2")
  private String usdrAddress2;
  
  @Column(name = "usdr_city_id", insertable = false, updatable = false)
  private Long usdrCityId;

  @Column(name = "usdr_modified_date")
  private LocalDateTime usdrModifiedDate;

  @ManyToOne
  @MapsId("usdrEntityId")
  @JoinColumn(name = "usdr_entityid")
  @JsonBackReference
  User user;

//  @MapsId("usdrCityId")
//  @ManyToOne
//  @JoinColumn(name = "usdr_city_id")
//  @JsonBackReference
//  private Cities city;
}
