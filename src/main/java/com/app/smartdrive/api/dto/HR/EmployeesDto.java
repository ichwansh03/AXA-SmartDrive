package com.app.smartdrive.api.dto.HR;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeesDto implements Serializable{
    private Long usdrId;
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
    
}
