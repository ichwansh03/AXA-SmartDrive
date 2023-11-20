package com.app.smartdrive.service_orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//service order workorder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoWorkorderDto {

    private String sowoName;
    private String sowoStatus;
}
