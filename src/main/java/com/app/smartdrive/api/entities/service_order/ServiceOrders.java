package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
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

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sero_arwg_code")
//    private AreaWorkGroup areaWorkGroup;

    @OneToMany(mappedBy = "serviceOrders", cascade = CascadeType.ALL)
    private List<ServiceOrderTasks> serviceOrderTasks;

    //directly to partner (many to one)
    @OneToMany(mappedBy = "caevServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetEvidence> claimAssetEvidence;

    //directly to partner (many to one)
    @OneToMany(mappedBy = "caspServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetSparepart> claimAssetSparepart;
}