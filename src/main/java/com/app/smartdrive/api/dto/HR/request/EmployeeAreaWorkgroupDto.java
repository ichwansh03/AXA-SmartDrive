package com.app.smartdrive.api.dto.HR.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAreaWorkgroupDto {
    private String zoneName;
    private String provinsi;
    private Long cityId;
    private String areaworkGroup;
    private Long empEntityId;
}
