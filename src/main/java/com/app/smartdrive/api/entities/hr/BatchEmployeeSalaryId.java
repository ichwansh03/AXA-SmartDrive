package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
public class BatchEmployeeSalaryId implements Serializable{
   
    @Column(name="besa_emp_entity_id",nullable = false)
    private Long besaEmpEntityid;
    
    @Column(name = "besa_created_date",nullable = false)
    private LocalDateTime besaCreatedDate;

}
