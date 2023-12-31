package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "services", schema = "so",
        uniqueConstraints = @UniqueConstraint(columnNames = "serv_creq_entityid"))
@NamedQuery(
        name = "Services.getServiceParent",
        query = "SELECT a FROM Services a INNER JOIN Services b ON a.servId = b.parentServices.servId WHERE a.users.userEntityId = ?1")
@DynamicInsert
@DynamicUpdate
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serv_id", updatable = false, nullable = false)
    private Long servId;

    @Column(name = "serv_created_on")
    private LocalDateTime servCreatedOn;

    @Column(name = "serv_type")
    @Enumerated(EnumType.STRING)
    private EnumCustomer.CreqType servType;

    @Column(name = "serv_insuranceno")
    private String servInsuranceNo;

    @Column(name = "serv_vehicleno")
    private String servVehicleNumber;

    @Column(name = "serv_startdate")
    private LocalDateTime servStartDate;

    @Column(name = "serv_enddate")
    private LocalDateTime servEndDate;

    @Column(name = "serv_status")
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.ServStatus servStatus;

    @ManyToOne
    @JoinColumn(name = "serv_serv_id")
    Services parentServices;

    @JsonIgnore
    @OneToMany(mappedBy = "parentServices")
    private List<Services> servicesList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serv_cust_entityid")
    private User users;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "serv_creq_entityid")
    private CustomerRequest customer;

    @JsonIgnore
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private List<ServiceOrders> serviceOrdersSet;

    @OneToOne(mappedBy = "services", cascade = CascadeType.ALL)
    private ServicePremi servicePremi;

    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private List<ServicePremiCredit> servicePremiCredits;
}
