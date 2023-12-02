package com.app.smartdrive.api.dto.service_order.response;

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

    private Long sowoId;
    private String sowoName;
    private Boolean sowoStatus;
}
