package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Data
public class BatchEmployeeSalaryId implements Serializable{
    
    private LocalDateTime besaCreatedDate;

}
