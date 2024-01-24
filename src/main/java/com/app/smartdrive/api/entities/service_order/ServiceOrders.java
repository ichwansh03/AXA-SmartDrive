package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NamedQueries({
        @NamedQuery(
                name = "ServiceOrders.selectPartner",
                query = "UPDATE ServiceOrders sero SET sero.partner = :partner WHERE sero.seroId = :seroId", lockMode = LockModeType.PESSIMISTIC_WRITE),
        @NamedQuery(
                name = "ServiceOrders.requestClosePolis",
                query = "UPDATE ServiceOrders sero SET sero.seroStatus = :seroStatus, sero.seroReason = :seroReason WHERE sero.seroId = :seroId", lockMode = LockModeType.PESSIMISTIC_WRITE),
})
@DynamicInsert
@DynamicUpdate
public class ServiceOrders {

    @Id
    private String seroId;

    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus = EnumModuleServiceOrders.SeroStatus.OPEN;

    private String seroReason;

    private String servClaimNo;

    private LocalDateTime servClaimStartdate;

    private LocalDateTime servClaimEnddate;

    @Column(name = "sero_agent_entityid")
    private Long seroAgentEntityid;

    //should be to partner area workgroup
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sero_part_id")
    private Partner partner;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_agent_entityid", referencedColumnName = "eawg_id", insertable = false, updatable = false)
    private EmployeeAreaWorkgroup employees;

    @OneToMany(mappedBy = "serviceOrders", cascade = CascadeType.ALL)
    private List<ServiceOrderTasks> serviceOrderTasks;

    //directly to partner (many to one)
    @OneToMany(mappedBy = "caevServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetEvidence> claimAssetEvidence;

    //directly to partner (many to one)
    @OneToMany(mappedBy = "caspServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetSparepart> claimAssetSparepart;
}