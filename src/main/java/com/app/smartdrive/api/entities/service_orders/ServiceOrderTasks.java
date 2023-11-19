package com.app.smartdrive.api.entities.service_orders;

import com.app.smartdrive.api.entities.service_orders.enumerated.EnumModuleServiceOrders;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
