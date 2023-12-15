package com.app.smartdrive.api.dto.HR.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAreaWorkgroupRequestDto {
    private String zoneName;
    private String provinsi;
    private Long cityId;
    private String arwgCode;
    private Long empEntityid;
}
