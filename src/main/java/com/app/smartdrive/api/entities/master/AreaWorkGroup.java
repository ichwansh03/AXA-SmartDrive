package com.app.smartdrive.api.entities.master;

import java.util.List;

import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "area_workgroup", schema = "mtr")
public class AreaWorkGroup {
    @Id
    @Column(name = "arwg_code", updatable = false, nullable = false)
    private String arwgCode;

    @Column(name = "arwg_desc")
    private String arwgDesc;

    @Column(name = "arwg_city_id")
    private int arwgCityId;

    @ManyToOne
    @JoinColumn(name = "arwg_city_id", insertable = false, updatable = false)
    private Cities cities;

    @OneToMany(mappedBy = "areaWorkGroup", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private List<EmployeeAreaWorkgroup> employeeAreaWorkgroup;
}
