package com.app.smartdrive.service_orders;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.service_orders.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

/**
 * <a href="https://www.baeldung.com/jpa-mapping-single-entity-to-multiple-tables#multiple-entities">Mapping Entity JPA</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services", schema = "so")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serv_id", updatable = false, nullable = false)
    private Long servId;

    @Column(name = "serv_created_on")
    private LocalDate servCreatedOn;

    @Column(name = "serv_type")
    @Size(max = 15)
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.ServType servType;

    @Column(name = "serv_insuranceNo")
    @Size(max = 12)
    private String servInsuranceNo;

    @Column(name = "serv_vehicleNo")
    @Size(max = 12)
    private String servVehicleNumber;

    @Column(name = "serv_startdate")
    private LocalDate servStartDate;

    @Column(name = "serv_enddate")
    private LocalDate servEndDate;

    @Column(name = "serv_status")
    @Size(max = 15)
    private String servStatus;

    //this field is FK, references to servId
    @Column(name = "serv_serv_id")
    private Long servServId;

    @Column(name = "serv_cust_entityid")
    private Long servCustEntityId;

    @Column(name = "serv_creq_entityid")
    private Long servCreqEntityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serv_serv_id", referencedColumnName = "serv_id", insertable = false, updatable = false)
    Services parentServices;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serv_cust_entityid", referencedColumnName = "user_entityid", insertable = false, updatable = false)
    User users;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "serv_creq_entityid", referencedColumnName = "creq_entityid",insertable = false, updatable = false)
    CustomerRequest customer;

    @JsonIgnore
    @OneToMany(mappedBy = "services", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ServiceOrders> serviceOrdersSet;
}
