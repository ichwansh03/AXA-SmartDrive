package com.app.smartdrive.api.dto.service_order.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Long sowoSeotId;
}
