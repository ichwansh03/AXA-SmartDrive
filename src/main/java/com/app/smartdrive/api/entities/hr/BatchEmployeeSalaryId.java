package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEmployeeSalaryId implements Serializable {
    
    private Long besaEmpEntityid;

    private LocalDateTime besaCreatedDate;
}
