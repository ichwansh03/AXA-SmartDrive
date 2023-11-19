package com.app.smartdrive.api.entities.hr;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="employee_salary_detail",schema="hr")
public class EmployeeSalaryDetail {
    
    @Id
    @Column(name="emsa_id")
    private Long emsaId;

    @Id
    @OneToOne(mappedBy = "employees", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="emp_entityid")
    private Employees EmsaEmpEntityid;

     @Id
     @Column(name="emsa_created_date")
     private Date emsaCreatedDate;

     @Column(name="emsa_name", length = 55)
     private String emsaName;

     @Column(name="emsa_subtotal")
     private Double emsaSubtotal;

}
