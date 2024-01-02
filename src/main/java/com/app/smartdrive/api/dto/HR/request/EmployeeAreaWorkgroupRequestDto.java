package com.app.smartdrive.api.dto.HR.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAreaWorkgroupRequestDto {
    private Long cityId;
    private String arwgCode;
    private Long empEntityid;
}
