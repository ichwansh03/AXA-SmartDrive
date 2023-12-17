package com.smartdrive.serviceorderservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_orders", schema = "so")
@NamedQuery(
        name = "ServiceOrders.selectPartner",
        query = "UPDATE ServiceOrders sero SET sero.partner = :partner WHERE sero.seroId = :seroId", lockMode = LockModeType.PESSIMISTIC_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServiceOrders {

    @Id
    @Column(name = "sero_id", unique = true)
    private String seroId;

    @Column(name = "sero_ordt_type")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;

    @Column(name = "sero_status")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus = EnumModuleServiceOrders.SeroStatus.OPEN;

    @Column(name = "sero_reason")
    private String seroReason;

    @Column(name = "serv_claim_no")
    private String servClaimNo;

    @Column(name = "serv_claim_startdate")
    private LocalDateTime servClaimStartdate;

    @Column(name = "serv_claim_enddate")
    private LocalDateTime servClaimEnddate;

    @Column(name = "sero_agent_entityid")
    private Long seroAgentEntityid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sero_sero_id")
    private ServiceOrders parentServiceOrders;

    @OneToMany(mappedBy = "parentServiceOrders")
    private List<ServiceOrders> serviceOrdersList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_serv_id")
    private Services services;

    @OneToMany(mappedBy = "serviceOrders", cascade = CascadeType.ALL)
    private List<ServiceOrderTasks> serviceOrderTasks;

    //directly to partner (many to one)
    @OneToMany(mappedBy = "caevServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetEvidence> claimAssetEvidence;

    //directly to partner (many to one)
    @OneToMany(mappedBy = "caspServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetSparepart> claimAssetSparepart;
}