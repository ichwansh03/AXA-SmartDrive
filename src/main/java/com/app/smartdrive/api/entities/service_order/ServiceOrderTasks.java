package com.app.smartdrive.api.entities.service_order;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "service_order_tasks", schema = "so")
@NamedQuery(
        name = "ServiceOrderTasks.updateTasksStatus",
        query = "UPDATE ServiceOrderTasks seot SET seot.seotStatus = :seotStatus WHERE seot.seotId = :seotId", lockMode = LockModeType.PESSIMISTIC_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServiceOrderTasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seotId;

    private String seotName;

    private LocalDateTime seotStartdate;

    private LocalDateTime seotEnddate;

    private LocalDateTime seotActualStartdate;

    private LocalDateTime seotActualEnddate;

    @Enumerated(EnumType.STRING)
    private EnumModuleServiceOrders.SeotStatus seotStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seot_arwg_code")
    private AreaWorkGroup areaWorkGroup;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seot_sero_id")
    private ServiceOrders serviceOrders;

    @JsonManagedReference
    @OneToMany(mappedBy = "serviceOrderTasks", cascade = CascadeType.ALL)
    private List<ServiceOrderWorkorder> serviceOrderWorkorders;

}
