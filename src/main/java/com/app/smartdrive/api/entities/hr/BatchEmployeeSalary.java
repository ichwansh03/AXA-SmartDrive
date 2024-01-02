package com.app.smartdrive.api.entities.hr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@IdClass(BatchEmployeeSalaryId.class)
@Table(name="batch_employee_salary",schema="hr")
public class BatchEmployeeSalary {

    @Id
    @Column(name = "besa_emp_entity_id", nullable = false)
    private Long besaEmpEntityid;

    @Id
    @Column(name = "besa_created_date",nullable = false)
    private LocalDate besaCreatedDate;

    @Column(name="ems_trasfer_Date")
    private LocalDateTime emsTrasferDate;

    @Column(name="besa_total_salary")
    private BigDecimal besaTotalSalary;

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

    @JsonBackReference
    @ManyToOne
    @MapsId("besaEmpEntityid")
    @JoinColumn(name = "besa_emp_entity_id")
    private Employees employees;

   


    


}
