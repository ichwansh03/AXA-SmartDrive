package com.app.smartdrive.api.entities.hr;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSalaryDetailId {
    private Long emsaId;
    private Long emsaEmpEntityid;
    private Date emsaCreatedDate;
}
