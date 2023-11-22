package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
public class EmployeeSalaryDetailId implements Serializable{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emsa_id")
    private Long emsaId;

    @Column(name = "emsa_create_date")
    private Date emsaCreateDate;
}
