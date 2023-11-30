package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_order_workorder", schema = "so")
@NamedQuery(
        name = "ServiceOrderWorkorder.findBySowoId",
        query = "SELECT sowo FROM ServiceOrderWorkorder sowo JOIN sowo.serviceOrderTasks seot WHERE sowo.sowoId = :sowoId")
public class ServiceOrderWorkorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sowo_id")
    private Long sowoId;

    @Column(name = "sowo_name")
    private String sowoName;

    @Column(name = "sowo_modified_date")
    private LocalDateTime sowoModDate;

    @Column(name = "sowo_status")
    @Size(max = 15, message = "work order status can't more than 15 characters")
    private String sowoStatus;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sowo_seot_id")
    private ServiceOrderTasks serviceOrderTasks;
}
