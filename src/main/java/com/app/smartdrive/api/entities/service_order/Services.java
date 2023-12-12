package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services", schema = "so", uniqueConstraints = @UniqueConstraint(columnNames = "serv_creq_entityid"))
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
    private EnumModuleServiceOrders.ServStatus servStatus = EnumModuleServiceOrders.ServStatus.ACTIVE;;

    @ManyToOne
    @JoinColumn(name = "serv_serv_id")
    Services parentServices;

    @JsonIgnore
    @OneToMany(mappedBy = "parentServices")
    private List<Services> servicesList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serv_cust_entityid")
    private User users;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "serv_creq_entityid")
    private CustomerRequest customer;

    @JsonIgnore
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private List<ServiceOrders> serviceOrdersSet;

    //@PrimaryKeyJoinColumn
    //@JsonIgnore
    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private List<ServicePremi> servicePremiSet;

    @OneToMany(mappedBy = "services", cascade = CascadeType.ALL)
    private List<ServicePremiCredit> servicePremiCredits;
}
