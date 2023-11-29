package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_order_workorder", schema = "so")
public class ServiceOrderWorkorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sowo_id", insertable = false, updatable = false)
    private Long sowoId;

    @Column(name = "sowo_id")
    private String sowoName;

    @LastModifiedDate
    @Column(name = "sowo_modified_date")
    private LocalDateTime sowoModDate;

    @Column(name = "sowo_status")
    @Size(max = 15, message = "work order status can't more than 15 characters")
    private String sowoStatus;

    @Column(name = "sowo_seot_id")
    private Long sowoSeotId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sowo_seot_id", referencedColumnName = "seot_id", insertable = false, updatable = false)
    private ServiceOrderTasks serviceOrderTasks;
}
