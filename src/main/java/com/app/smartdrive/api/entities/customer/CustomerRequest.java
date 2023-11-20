package com.app.smartdrive.api.entities.customer;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customerRequest", schema = "customer")
@Entity
public class CustomerRequest {
    @Id
    @Column(name = "creq_entityid")
    private Long creqEntityId;

    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "creq_entityid")
    private BusinessEntity businessEntity;
        
    @Column(name = "creq_create_date")
    private LocalDateTime creqCreateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "creq_status", length = 15)
    private EnumCustomer.CreqStatus creqStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "creq_type", length = 15)
    private EnumCustomer.CreqType creqType;

    @Column(name = "creq_modified_date")
    private LocalDateTime creqModifiedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creq_cust_entityid")
    private User customer;

    
    @JsonManagedReference
    @OneToOne(mappedBy = "customerRequest", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CustomerClaim customerClaim;

    @JsonManagedReference
    @OneToOne(mappedBy = "customerRequest", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CustomerInscAssets customerInscAssets;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "creq_agen_entityid")
    private Employees employee;

}
