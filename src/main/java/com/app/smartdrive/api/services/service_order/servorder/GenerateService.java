package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;

import java.time.LocalDateTime;

public interface GenerateService {

    Services generateFeasiblityType(CustomerRequest cr);

    Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus);

    void generateServPremi(Services services);
}
