package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_orders", schema = "so")
public class ServiceOrders {

    @Id
    @Column(name = "sero_id", unique = true)
    @Size(max = 25)
    private String seroId;

    @Column(name = "sero_ordt_type")
    @Size(max = 15)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;

    @Column(name = "sero_status")
    @Size(max = 15)
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus;

    @Column(name = "sero_reason")
    private String seroReason;

    @Column(name = "serv_claim_no")
    @Size(max = 12)
    private String servClaimNo;

    @Column(name = "serv_claim_startdate")
    private LocalDate servClaimStartdate;

    @Column(name = "serv_claim_enddate")
    private LocalDate servClaimEnddate;

    @Column(name = "sero_serv_id")
    private Long seroServId;

    @Column(name = "sero_sero_id")
    private String seroSeroId;

    @Column(name = "sero_agent_entityid")
    private Long seroAgentEntityid;

    @Column(name = "sero_arwg_code")
    @Size(max = 15)
    private String seroArwgCode;

    //add referenced column
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sero_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false, unique = true)
    Services services;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders parentServiceOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_agent_entityid", referencedColumnName = "emp_entityid", insertable = false, updatable = false)
    Employees employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_arwg_code", referencedColumnName = "arwg_code", insertable = false, updatable = false)
    AreaWorkGroup areaWorkGroup;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceOrders", cascade = CascadeType.ALL)
    Set<ServiceOrderTasks> serviceOrderTasksSet;

    @JsonIgnore
    @OneToMany(mappedBy = "caevServiceOrders", cascade = CascadeType.ALL)
    Set<ClaimAssetEvidence> claimAssetEvidenceSet;

    @JsonIgnore
    @OneToMany(mappedBy = "caspServiceOrders", cascade = CascadeType.ALL)
    Set<ClaimAssetSparepart> claimAssetSparepartSet;
}