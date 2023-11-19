package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "business_entity", schema = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userBusinessEntity")
public class BusinessEntity {
  @Id
  @Column(name = "entityid", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long entityId;

  @Column(name = "entity_modified_date")
  private LocalDateTime entityModifiedDate;
  
  @OneToOne(mappedBy = "userEntityId",cascade = CascadeType.ALL)
  private User user;

  
  @OneToOne(mappedBy = "businessEntity", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private Banks banks;

  @OneToOne(mappedBy = "businessEntity", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private Fintech fintech;
}
