package com.app.smartdrive.api.entities.service_order;

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

@Builder
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
    private LocalDateTime seotStartDate;

    @Column(name = "seot_enddate")
    private LocalDateTime seotEndDate;

    @Column(name = "seot_actual_startdate")
    private LocalDateTime seotActualStartdate;

    @Column(name = "seot_actual_enddate")
    private LocalDateTime seotActualEnddate;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seot_arwg_code", referencedColumnName = "arwg_code", insertable = false, updatable = false)
    AreaWorkGroup areaWorkGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seot_sero_id", referencedColumnName = "sero_id", insertable = false, updatable = false)
    ServiceOrders serviceOrders;

    @JsonIgnore
    @OneToMany(mappedBy = "serviceOrderTasks", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ServiceOrderWorkorder> serviceOrderWorkorders;
}
