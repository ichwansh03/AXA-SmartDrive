package com.app.smartdrive.api.entities.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.payment.Banks;
import com.app.smartdrive.api.entities.payment.Fintech;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Entity;

// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userBusinessEntity")
@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "business_entity", schema = "users")
public class BusinessEntity {

  @Id
  @Column(name = "entityid", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long entityId;

  @LastModifiedDate
  @Column(name = "entity_modified_date")
  private LocalDateTime entityModifiedDate;
  
  @OneToOne(mappedBy = "userBusinessEntity",cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private User user;

  
  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private Banks banks;

  @OneToOne(mappedBy = "businessEntity", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  @JsonManagedReference
  private Fintech fintech;

  @JsonManagedReference
  @OneToMany(mappedBy = "businessEntity",cascade = CascadeType.ALL)
  private List<CustomerRequest> customerRequest = new ArrayList<>();

public Long getBanksById(Long businessEntityId) {
    return null;
}

}
