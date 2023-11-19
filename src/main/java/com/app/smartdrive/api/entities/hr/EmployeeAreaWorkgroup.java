package com.app.smartdrive.api.entities.hr;

import java.time.LocalDateTime;


import org.springframework.data.annotation.Id;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="employee_area_workgroup",schema="hr")
public class EmployeeAreaWorkgroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eawg_id")
    private Long eawgId;
    
    @Id
    @OneToOne(mappedBy = "employees", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="emp_entityid")
    private Employees EawgEmpEntityid;

   

    @Column(name="eawg_modified_date")
    private LocalDateTime empGraduate;
}
