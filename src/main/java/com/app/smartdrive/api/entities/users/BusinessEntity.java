package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userBusinessEntity")
@Data
@Entity
@NoArgsConstructor
@Table(name = "business_entity", schema = "users")
public class BusinessEntity {

  @Id
  @Column(name = "entityid", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long entityId;

  @Column(name = "entity_modified_date")
  private LocalDateTime entityModifiedDate;
  
  @OneToOne(mappedBy = "userBusinessEntity",cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private User user;
}
