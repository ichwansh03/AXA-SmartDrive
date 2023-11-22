package com.app.smartdrive.api.entities.hr;

import java.time.LocalDateTime;

import com.app.smartdrive.api.entities.hr.EnumClassHR.status;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="batch_employee_salary",schema="hr")
public class BatchEmployeeSalary {
 
    @EmbeddedId
    private BatchEmployeeSalaryId batchEmployeeSalaryId;

    @Column(name="ems_trasfer_Date")
    private LocalDateTime emsTrasferDate;

    @Column(name="besa_total_salary")
    private Double besaTotalSalary;

    @Column(name="besa_account_number", length = 35)
    private String besaAccountNumber;

    @Column(name="besa_status", length = 15)
    @Enumerated(EnumType.STRING)
    private EnumClassHR.status besaStatus;

    @Column(name="besa_patr_trxno", length = 55)
    private String besaPatrTrxno;

    @Column(name="besa_paid_date")
    private LocalDateTime besaPaidDate;

    @Column(name="besa_modified_date")
    private LocalDateTime besaModifiedDate;

    @ManyToOne
    @MapsId("besaEmpEntityid")
    @JoinColumn(name = "besa_emp_entity_id")
    @JsonBackReference
    private Employees employees;

   


    


}
