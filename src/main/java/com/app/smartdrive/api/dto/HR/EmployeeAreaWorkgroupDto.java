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
public class EmployeeAreaWorkgroupDto implements Serializable {
    private String zoneName;
    private String provinsi;
    private String cityName;
    private String areaworkGroup;
    private String empName;
    private Long empEntityid;
    
}
