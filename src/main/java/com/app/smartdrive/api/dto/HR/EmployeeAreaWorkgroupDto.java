package com.app.smartdrive.api.dto.HR;

import java.io.Serializable;

import lombok.Data;

@Data
public class EmployeeAreaWorkgroupDto implements Serializable {
    private String zoneName;
    private String provinsi;
    private String cityName;
    private String areaworkGroup;
    private String empName;
    private Long empEntityid;
    
}
