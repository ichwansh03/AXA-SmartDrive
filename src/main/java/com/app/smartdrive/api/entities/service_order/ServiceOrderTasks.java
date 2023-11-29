package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@NamedQuery(
        name = "ServiceOrderTasks.findSeotById",
        query = "SELECT seot FROM ServiceOrderTasks seot JOIN seot.serviceOrders sero " +
                "JOIN seot.areaWorkGroup arwg WHERE seot.seotId = :seotId"
)
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
    private String seotStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seot_arwg_code")
    AreaWorkGroup areaWorkGroup;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seot_sero_id")
    ServiceOrders serviceOrders;

    @JsonManagedReference
    @OneToMany(mappedBy = "serviceOrderTasks", cascade = CascadeType.ALL)
    List<ServiceOrderWorkorder> serviceOrderWorkorders;
}
