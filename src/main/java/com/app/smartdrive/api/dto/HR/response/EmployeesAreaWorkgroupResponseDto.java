package com.app.smartdrive.api.dto.HR.response;

import com.app.smartdrive.api.dto.master.response.ArwgRes;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeesAreaWorkgroupResponseDto {
    private ArwgRes areaWorkGroup;
    private EmployeesDto employees;
}