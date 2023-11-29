package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services", schema = "so")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serv_id", updatable = false, nullable = false)
    private Long servId;

    //CustomerRequest.creqCreateDate
    @Column(name = "serv_created_on")
    private LocalDateTime servCreatedOn;

    //CustomerRequest.creqType
    @Column(name = "serv_type")
    @Enumerated(EnumType.STRING)
    private EnumCustomer.CreqType servType;

    @Column(name = "serv_insuranceno")
    @Size(max = 12, message = "insurance number can't more than 12 character")
    private String servInsuranceNo;

    //CustomerInscAssets.ciasPoliceNumber
    @Column(name = "serv_vehicleno")
    @Size(max = 12, message = "vehicle number can't more than 12 character")
    private String servVehicleNumber;

    //CustomerInscAssets.ciasStartDate
    @Column(name = "serv_startdate")
    private LocalDateTime servStartDate;

    //CustomerInscAssets.ciasEndDate
    @Column(name = "serv_enddate")
    private LocalDateTime servEndDate;

    //CustomerRequest.creqStatus
    @Column(name = "serv_status")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.ServStatus servStatus;
    {
        servStatus = EnumModuleServiceOrders.ServStatus.ACTIVE;
    }

    @Column(name = "serv_serv_id")
    private Long servServId;

    //User.userEntityid
    @Column(name = "serv_cust_entityid")
    private Long servCustEntityid;

    //Customer.Request.creqEntityid
    @Column(name = "serv_creq_entityid")
    private Long servCreqEntityid;

    @ManyToOne
    @JoinColumn(name = "serv_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services parentServices;

    @JsonIgnore
    @OneToMany(mappedBy = "parentServices")
    private List<Services> servicesList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serv_cust_entityid", referencedColumnName = "user_entityid", insertable = false, updatable = false)
    private User users;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serv_creq_entityid", referencedColumnName = "creq_entityid", insertable = false, updatable = false)
    private CustomerRequest customer;

    @JsonIgnore
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private List<ServiceOrders> serviceOrdersSet;

    @JsonIgnore
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<ServicePremi> servicePremiSet;
}
