package com.app.smartdrive.api.entities.hr;

import java.time.LocalDateTime;




import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@IdClass(EmployeeAreaWorkgroupId.class)
@Table(name="employee_area_workgroup",schema="hr")
public class EmployeeAreaWorkgroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eawg_id")
    private Long eawgId;
    
    @Id
    @Column(name = "eawg_entityid")
    private Long eawgEntityid;

    @Column(name="eawg_modified_date")
    private LocalDateTime empGraduate;


    @Column(name = "eawg_arwg_code",length=15)
    private String eawgArwgCode;


    @ManyToOne
    @MapsId
    @JoinColumn(name = "eawg_arwg_code")
    @JsonBackReference
    private AreaWorkGroup areaWorkGroup;


    @ManyToOne
    @MapsId("eawgEntityid")
    @JoinColumn(name = "eawg_entityid")
    @JsonBackReference
    private Employees employees;

    
    
}
