package com.app.smartdrive.api.services.service_order.servorder.services;

import com.app.smartdrive.api.dto.service_order.response.ServiceDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;

import java.time.LocalDateTime;

public interface ServiceTransaction {

    ServiceDto addService(Long creqId);

    Services handleServiceUpdate(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus);

    Services buildCommonServiceData(CustomerRequest cr, LocalDateTime endDate, EnumModuleServiceOrders.ServStatus servStatus);
}
