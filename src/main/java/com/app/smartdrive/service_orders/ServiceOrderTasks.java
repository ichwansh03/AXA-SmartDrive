package com.app.smartdrive.service_orders;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.service_orders.enumerated.EnumModuleServiceOrders;
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
@Table(name = "service_order_tasks", schema = "so")
public class ServiceOrderTasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seot_id")
    private Long seotId;

    @Column(name = "seot_name")
    private String seotName;

    @Column(name = "seot_startdate")
    private LocalDate seotStartDate;

    @Column(name = "seot_enddate")
    private LocalDate seotEndDate;

    @Column(name = "seot_actual_startdate")
    private LocalDate seotActualStartdate;

    @Column(name = "seot_actual_enddate")
    private LocalDate seotActualEnddate;

    @Column(name = "seot_status")
    @Size(max = 15)
    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeotStatus seotStatus;

    @Column(name = "seot_arwg_code")
    @Size(max = 15)
    private String seotArwgCode;

    @Column(name = "seot_sero_id")
    @Size(max = 25)
    private String seotSeroId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seot_arwg_code", referencedColumnName = "arwg_code")
    AreaWorkGroup areaWorkGroup;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seot_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders serviceOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceOrderTasks", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<ServiceOrderWorkorder> serviceOrderWorkordersSet;
}
