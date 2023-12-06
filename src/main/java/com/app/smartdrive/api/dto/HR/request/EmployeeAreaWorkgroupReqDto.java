package com.app.smartdrive.api.dto.HR.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAreaWorkgroupReqDto {
    private String zoneName;
    private String provinsi;
    private String cityName;
    private String areaworkGroup;
    private String empName;
}
