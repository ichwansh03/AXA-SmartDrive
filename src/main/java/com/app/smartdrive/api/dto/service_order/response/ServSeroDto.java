package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ServSeroDto {

    private String seroId;
    private EnumCustomer.CreqType servType;
    private LocalDateTime servCreatedOn;
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    private User user;
    private List<SoTasksDto> serviceOrderTasksList;

    public ServSeroDto(String seroId, EnumCustomer.CreqType servType, LocalDateTime servCreatedOn, EnumModuleServiceOrders.SeroStatus seroStatus, User user) {
        this.seroId = seroId;
        this.servType = servType;
        this.servCreatedOn = servCreatedOn;
        this.seroStatus = seroStatus;
        this.user = user;
    }
}
