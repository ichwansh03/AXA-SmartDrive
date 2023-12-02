package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "service_order_workorder", schema = "so")
@NamedQuery(
        name = "ServiceOrderWorkorder.findBySowoId",
        query = "SELECT sowo FROM ServiceOrderWorkorder sowo JOIN sowo.serviceOrderTasks seot WHERE sowo.sowoId = :sowoId")
@DynamicInsert
@DynamicUpdate
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
    private Boolean sowoStatus;

    @Column(name = "sowo_order")
    private Integer sowoOrder;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sowo_seot_id")
    private ServiceOrderTasks serviceOrderTasks;

    public ServiceOrderWorkorder(String sowoName, LocalDateTime sowoModDate, Boolean sowoStatus, Integer sowoOrder, ServiceOrderTasks serviceOrderTasks) {
        this.sowoName = sowoName;
        this.sowoModDate = sowoModDate;
        this.sowoStatus = sowoStatus;
        this.sowoOrder = sowoOrder;
        this.serviceOrderTasks = serviceOrderTasks;
    }
}
