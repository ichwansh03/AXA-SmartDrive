package com.app.smartdrive.api.entities.hr;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@IdClass(EmployeeSalaryDetailId.class)
@Table(name="employee_salary_detail",schema="hr")
public class EmployeeSalaryDetail {
    
    @Id
    @Column(name="emsa_id")
    private Long emsaId;

    @Id
    @Column(name = "emsa_emp_entityid")
    private Long emsaEmpEntityid;

     @Id
     @Column(name="emsa_created_date")
     private Date emsaCreatedDate;

     @Column(name="emsa_name", length = 55)
     private String emsaName;

     @Column(name="emsa_subtotal")
     private Double emsaSubtotal;

    @ManyToOne
    @MapsId("emsaEmpEntityid")
    @JoinColumn(name = "emsa_emp_entityid")
    @JsonBackReference
    private Employees employees;

}
