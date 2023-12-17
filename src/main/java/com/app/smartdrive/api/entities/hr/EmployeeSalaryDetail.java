package com.app.smartdrive.api.entities.hr;



import java.math.BigDecimal;
import java.time.LocalDate;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@IdClass(EmployeeSalaryDetailId.class)
@Table(name="employee_salary_detail",schema="hr")
public class EmployeeSalaryDetail {

    @Id
    @Column(name = "emsa_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, 
      generator = "emsaId-generator")
    @SequenceGenerator(
      name = "emsaId-generator",
      sequenceName = "hr.emsa_id",
      allocationSize = 1, schema = "hr"
    )
    private Long emsaId;

    @Id
    @Column(name = "emsa_emp_entityid")
    private Long emsaEmpEntityid;

    @Id
    @Column(name = "emsa_create_date")
    private LocalDate emsaCreateDate;

     @Column(name="emsa_name", length = 55)
     private String emsaName;

     @Column(name="emsa_subtotal")
     private BigDecimal emsaSubtotal;

    @ManyToOne
    @JoinColumn(name = "emsa_emp_entityid",insertable=false, updatable=false)
    @JsonBackReference
    private Employees employees;

}
