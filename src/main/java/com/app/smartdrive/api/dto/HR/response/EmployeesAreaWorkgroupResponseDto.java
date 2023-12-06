package com.app.smartdrive.api.dto.HR.response;

import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.master.response.CitiesRes;
import com.app.smartdrive.api.dto.master.response.ProvRes;
import com.app.smartdrive.api.dto.master.response.ZonesRes;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.master.Zones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesAreaWorkgroupResponseDto {
    private ArwgRes areaworkGroup;
    private EmployeesDto employees;
}
