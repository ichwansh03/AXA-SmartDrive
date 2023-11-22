package com.app.smartdrive.api.entities.hr;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Data
public class EmployeeSalaryDetailId implements Serializable{
    private Long emsaEmpEntityid;
    private Date emsaCreateDate;
}
