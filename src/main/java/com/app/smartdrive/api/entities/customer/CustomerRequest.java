package com.app.smartdrive.api.entities.customer;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "customerRequest", schema = "customer")
@Entity
public class CustomerRequest {
    @Id
    @Column(name = "creq_entityid")
    private Long creqEntityId;

    @JsonBackReference
    @ManyToOne
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


    @ManyToOne
    @JoinColumn(name = "creq_cust_entityid")
    private User customer;

    @Column(name = "creq_agen_entityid")
    private Long creqAgenEntityid;


    @JsonManagedReference
    @OneToOne(mappedBy = "customerRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private CustomerClaim customerClaim;

    @JsonManagedReference
    @OneToOne(mappedBy = "customerRequest", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CustomerInscAssets customerInscAssets;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "creq_agen_entityid",
            referencedColumnName = "eawg_id",
            insertable = false,
            updatable = false
    )
    private EmployeeAreaWorkgroup employeeAreaWorkgroup;

    @JsonManagedReference
    @OneToOne(mappedBy = "customer")
    private Services services;
}