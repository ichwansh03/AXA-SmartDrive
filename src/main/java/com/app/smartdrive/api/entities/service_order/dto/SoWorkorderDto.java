package com.app.smartdrive.api.entities.service_order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//service order workorder
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoWorkorderDto {

    private String sowoName;
    private String sowoStatus;
}
