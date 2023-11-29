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

    @NotBlank(message = "service workorder name (sowoName) can't be blank")
    private String sowoName;
    @NotBlank(message = "service workorder status (sowoStatus) can't be blank")
    private String sowoStatus;
    @NotNull(message = "service order task id (sowoSeotId) can't be null")
    private Long sowoSeotId;
}
