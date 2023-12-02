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
@NamedQueries({
        @NamedQuery(
                name = "ServiceOrderTasks.findSeotBySeroId",
                query = "SELECT seot FROM ServiceOrderTasks seot WHERE seot.serviceOrders.seroId = :seroId"),
        @NamedQuery(
                name = "ServiceOrderTasks.updateTasksStatus",
                query = "UPDATE ServiceOrderTasks seot SET seot.seotStatus = :seotStatus WHERE seot.seotId = :seotId", lockMode = LockModeType.PESSIMISTIC_WRITE)})
@DynamicInsert
@DynamicUpdate
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
    private String seotStatus = EnumModuleServiceOrders.SeotStatus.INPROGRESS.toString();

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

    public ServiceOrderTasks(String seotName, LocalDateTime seotActualStartdate, LocalDateTime seotActualEnddate, AreaWorkGroup areaWorkGroup, ServiceOrders serviceOrders) {
        this.seotName = seotName;
        this.seotActualStartdate = seotActualStartdate;
        this.seotActualEnddate = seotActualEnddate;
        this.areaWorkGroup = areaWorkGroup;
        this.serviceOrders = serviceOrders;
    }
}
