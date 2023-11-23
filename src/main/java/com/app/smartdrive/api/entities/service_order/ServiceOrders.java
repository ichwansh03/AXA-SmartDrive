package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_orders", schema = "so")
public class ServiceOrders {

    //format seroId (condition serv type)
    @Id
    @Column(name = "sero_id", unique = true)
    @Size(max = 25, message = "Service order ID format can't more than 25 characters")
    private String seroId;

    //Services.servType
    @Column(name = "sero_ordt_type")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;
    {
        seroOrdtType = EnumModuleServiceOrders.SeroOrdtType.CLOSE;
    }

    //CustomerRequest.creqStatus
    @Column(name = "sero_status")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    {
        seroStatus = EnumModuleServiceOrders.SeroStatus.CLOSED;
    }

    //CustomerClaim.cuclReason
    @Column(name = "sero_reason")
    private String seroReason;

    @Column(name = "serv_claim_no")
    @Size(max = 12, message = "claim number can't more than 12 characters")
    private String servClaimNo;

    @Column(name = "serv_claim_startdate")
    private LocalDateTime servClaimStartdate;

    @Column(name = "serv_claim_enddate")
    private LocalDateTime servClaimEnddate;

    //Services.servId
    @Column(name = "sero_serv_id")
    private Long seroServId;

    //ServiceOrders.seroId
    @Column(name = "sero_sero_id")
    private String seroSeroId;

    //CustomerRequest.creqAgenEntityid
    @Column(name = "sero_agent_entityid")
    private Long seroAgentEntityid;

    //AreaWorkGroup.arwgCode
    @Column(name = "sero_arwg_code")
    @Size(max = 15)
    private String seroArwgCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services services;

    @OneToOne
    @JoinColumn(name = "sero_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders parentServiceOrders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_agent_entityid", referencedColumnName = "emp_entityid", insertable = false, updatable = false)
    Employees employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_arwg_code", referencedColumnName = "arwg_code", insertable = false, updatable = false)
    AreaWorkGroup areaWorkGroup;

    @ManyToOne
    @JoinColumn(name = "sero_agent_entityid")
    private Employees employee;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ServiceOrderTasks> serviceOrderTasks;

    @JsonIgnore
    @OneToMany(mappedBy = "caevServiceOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ClaimAssetEvidence> claimAssetEvidence;

    @JsonIgnore
    @OneToMany(mappedBy = "caspServiceOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ClaimAssetSparepart> claimAssetSparepart;
}