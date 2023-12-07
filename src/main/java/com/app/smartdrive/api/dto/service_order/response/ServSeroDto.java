package com.app.smartdrive.api.dto.service_order.response;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServSeroDto {

    private String seroId;
    private EnumCustomer.CreqType servType;
    private LocalDateTime servCreatedOn;
    private EnumModuleServiceOrders.SeroStatus seroStatus;
    private String userName;
    private String empName;
    private List<SoTasksDto> serviceOrderTasksList;

}
