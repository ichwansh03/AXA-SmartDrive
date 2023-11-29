package com.app.smartdrive.api.entities.master;

import java.util.List;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "area_workgroup", schema = "mtr")
public class AreaWorkGroup {
    @Id
    @Column(name = "arwg_code", updatable = false, nullable = false)
    private String arwgCode;

    @Column(name = "arwg_desc")
    private String arwgDesc;

    @Column(name = "arwg_city_id")
    private Long arwgCityId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "arwg_city_id", insertable = false, updatable = false)
    private Cities cities;

    @PrimaryKeyJoinColumn
    @OneToMany(mappedBy = "areaWorkGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EmployeeAreaWorkgroup> employeeAreaWorkgroup;

    //    @JsonIgnore
    @OneToMany(mappedBy = "areaWorkGroup")
    private List<ServiceOrders> serviceOrders;

    //    @JsonIgnore
    @OneToMany(mappedBy = "areaWorkGroup")
    private List<ServiceOrderTasks> serviceOrderTasks;
}