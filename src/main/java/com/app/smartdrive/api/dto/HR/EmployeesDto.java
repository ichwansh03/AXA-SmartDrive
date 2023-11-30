package com.app.smartdrive.api.dto.HR;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesDto {
    private String empName;
    private String empEmail;
    private String empPhone;
    private String empJoinDate;
    private String empGraduate;
    private String empRole;
    private Double empSalary;
    private String empAccountNumber;
    private String empCity;
    private String empAddress;
    private String empAddress2;
    private Boolean GrantUserAccess ;
}
