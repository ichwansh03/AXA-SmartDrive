package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryDetailId implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emsa_id")
    private Long emsaId;

    @Column(name = "emsa_emp_entityid",insertable=false, updatable=false)
    private Long emsaEmpEntityid;

    @Column(name = "emsa_create_date")
    private LocalDate emsaCreateDate;
}
