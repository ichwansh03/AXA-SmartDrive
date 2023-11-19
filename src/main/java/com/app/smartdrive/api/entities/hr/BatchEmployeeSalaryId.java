package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEmployeeSalaryId implements Serializable {
    
    private Long besaEmpEntityid;

    private Long besaCreatedDate;
}
