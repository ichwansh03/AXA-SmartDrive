package com.app.smartdrive.api.entities.hr;

import java.util.Date;



import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name="employee_salary_detail",schema="hr")
public class EmployeeSalaryDetail {

    @EmbeddedId
    private EmployeeSalaryDetailId employeeSalaryDetailId;

     @Column(name="emsa_name", length = 55)
     private String emsaName;

     @Column(name="emsa_subtotal")
     private Double emsaSubtotal;

//    @MapsId("emsaEmpEntityid")
    @ManyToOne
    @JoinColumn(name = "emsa_emp_entityid")
    @JsonBackReference
    private Employees employees;

}
