package com.app.smartdrive.api.dto.master;

import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.service_order.ServicesDto;
import com.app.smartdrive.api.dto.service_order.SoTasksDto;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaWorkGroupDto {
    private String arwgCode;

    @Size(max = 55, message = "Area Work Group Description Length Exceeded !")
    private String arwgDesc;

    private Long arwgCityId;
    private List<EmployeeAreaWorkgroupDto> empWoDto;
    private List<ServicesDto> servicesDto;
    private List<SoTasksDto> soTasksDto;
}
