package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
        name = "ServiceOrders.findServiceOrdersById",
        query = "SELECT sero FROM ServiceOrders sero JOIN sero.services s JOIN sero.employees emp" +
                " JOIN sero.areaWorkGroup arwg WHERE sero.seroId = :seroId")
@DynamicInsert
@DynamicUpdate
public class ServiceOrders {

    //format seroId (condition serv type)
    @Id
    @Column(name = "sero_id", unique = true)
    private String seroId;

    @Transient
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seroIden;

    //Services.servType
    @Column(name = "sero_ordt_type")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroOrdtType seroOrdtType;
    {
        seroOrdtType = EnumModuleServiceOrders.SeroOrdtType.CREATE;
    }

    //CustomerRequest.creqStatus
    @Column(name = "sero_status")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    {
        seroStatus = EnumModuleServiceOrders.SeroStatus.OPEN;
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

    @JsonManagedReference
    @OneToOne(mappedBy = "serviceOrders")
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
    @JoinColumn(name = "sero_agent_entityid")
    private Employees employees;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sero_arwg_code")
    private AreaWorkGroup areaWorkGroup;

    @OneToMany(mappedBy = "serviceOrders", cascade = CascadeType.ALL)
    private List<ServiceOrderTasks> serviceOrderTasks;

    @OneToMany(mappedBy = "caevServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetEvidence> claimAssetEvidence;

    @OneToMany(mappedBy = "caspServiceOrders", cascade = CascadeType.ALL)
    private List<ClaimAssetSparepart> claimAssetSparepart;
}