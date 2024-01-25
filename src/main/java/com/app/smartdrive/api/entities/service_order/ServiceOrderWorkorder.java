package com.app.smartdrive.api.entities.service_order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "service_order_workorder", schema = "so")
@NamedQuery(
        name = "ServiceOrderWorkorder.updateSowoStatus",
        query = "UPDATE ServiceOrderWorkorder sowo SET sowo.sowoStatus = :sowoStatus, sowo.sowoModifiedDate = :sowoModDate WHERE sowo.sowoId = :sowoId", lockMode = LockModeType.PESSIMISTIC_WRITE)
@DynamicUpdate
@DynamicInsert
@EntityListeners({AuditingEntityListener.class})
public class ServiceOrderWorkorder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sowoId;

    private String sowoName;

    @LastModifiedDate
    private LocalDateTime sowoModifiedDate;

    private Boolean sowoStatus;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sowo_seot_id")
    private ServiceOrderTasks serviceOrderTasks;

}
