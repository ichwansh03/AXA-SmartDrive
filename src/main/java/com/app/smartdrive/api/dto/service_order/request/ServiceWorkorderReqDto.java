package com.app.smartdrive.api.dto.service_order.request;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ServiceWorkorderReqDto {

    private String sowoName;
    @LastModifiedDate
    private LocalDateTime sowoModifiedDate;
    private Boolean sowoStatus;
    private ServiceOrderTasks serviceOrderTasks;

    public ServiceWorkorderReqDto(String sowoName, LocalDateTime sowoModifiedDate, Boolean sowoStatus, ServiceOrderTasks serviceOrderTasks) {
        this.sowoName = sowoName;
        this.sowoModifiedDate = sowoModifiedDate;
        this.sowoStatus = sowoStatus;
        this.serviceOrderTasks = serviceOrderTasks;
    }
}
