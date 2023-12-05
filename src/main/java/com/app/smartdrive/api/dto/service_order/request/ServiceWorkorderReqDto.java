package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ServiceWorkorderReqDto {

    @NotBlank
    private String sowoName;
    private LocalDateTime sowoModDate;
    @NotNull
    private Boolean sowoStatus;
    private ServiceOrderTasks serviceOrderTasks;

    public ServiceWorkorderReqDto(String sowoName, LocalDateTime sowoModDate, Boolean sowoStatus, ServiceOrderTasks serviceOrderTasks) {
        this.sowoName = sowoName;
        this.sowoModDate = sowoModDate;
        this.sowoStatus = sowoStatus;
        this.serviceOrderTasks = serviceOrderTasks;
    }
}
