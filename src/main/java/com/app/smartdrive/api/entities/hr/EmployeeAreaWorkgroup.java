package com.app.smartdrive.api.entities.hr;

import java.time.LocalDateTime;
import java.util.List;


import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="employee_are_workgroup",schema="hr")
public class EmployeeAreaWorkgroup {

    @Column(name = "eawg_id")
    private Long eawgId;

    @EmbeddedId
    private EmployeeAreaWorkgroupId employeeAreaWorkgroupId;

    @Column(name="eawg_modified_date")
    private LocalDateTime eawgModifiedDate;


    @Column(name = "eawg_arwg_code",length=15)
    private String eawgArwgCode;


    @MapsId("eawgArwgCode")
    @ManyToOne
    @JoinColumn(name = "eawg_arwg_code",insertable = false, updatable = false)
    @JsonBackReference
    private AreaWorkGroup areaWorkGroup;


    @ManyToOne
    @MapsId("eawgEntityid")
    @JoinColumn(name = "eawg_entityid")
    @JsonBackReference
    private Employees employees;


    @MapsId("eawgId")
    @JsonManagedReference
    @OneToMany(mappedBy = "employeeAreaWorkgroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerRequest> customerRequests;



}