package com.app.smartdrive.api.entities.service_orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "service_order_workorder", schema = "so")
public class ServiceOrderWorkorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sowo_id")
    private Long sowoId;

    @Column(name = "sowo_id")
    private String sowoName;

    @Column(name = "sowo_modified_date")
    private LocalDate sowoModDate;

    @Column(name = "sowo_status")
    private String sowoStatus;

    @Column(name = "sowo_seot_id")
    private Long sowoSeotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sowo_seot_id", referencedColumnName = "seot_id", insertable = false, updatable = false)
    ServiceOrderTasks serviceOrderTasks;
}
