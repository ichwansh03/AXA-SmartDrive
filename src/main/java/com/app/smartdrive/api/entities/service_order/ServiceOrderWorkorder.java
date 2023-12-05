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
@NamedQueries({
        @NamedQuery(
                name = "ServiceOrderWorkorder.findSowoBySeotId",
                query = "SELECT sowo FROM ServiceOrderWorkorder sowo WHERE sowo.serviceOrderTasks.seotId = :seotId"),
        @NamedQuery(
                name = "ServiceOrderWorkorder.updateSowoStatus",
                query = "UPDATE ServiceOrderWorkorder sowo SET sowo.sowoStatus = :sowoStatus WHERE sowo.sowoId = :sowoId", lockMode = LockModeType.PESSIMISTIC_WRITE)
})
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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sowo_seot_id")
    private ServiceOrderTasks serviceOrderTasks;

}
