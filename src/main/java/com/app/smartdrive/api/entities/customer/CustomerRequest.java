package com.app.smartdrive.api.entities.customer;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @OneToOne
    @MapsId
    @JoinColumn(name = "creq_entityid")
    private BusinessEntity userBusinessEntity;
        
    @Column(name = "creq_create_date")
    private LocalDateTime creqCreateDate;

    @Column(name = "creq_status", length = 15)
    private String creqStatus;

    @Column(name = "creq_type", length = 15)
    private String creqType;

    @Column(name = "creq_modified_date")
    private LocalDateTime creqModifiedDate;

    @ManyToOne
    @JoinColumn(name = "creq_cust_entityid")
    private User customer;

    // kurang hr.employee --creqAgen


    @OneToOne(mappedBy = "customerRequest", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CustomerClaim customerClaim;

    @OneToOne(mappedBy = "customerRequest", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CustomerInscAssets customerInscAssets;


}
