package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.dto.service_order.ServicesDto;
import com.app.smartdrive.api.entities.service_order.Services;


public interface SoOrderService {

    ServicesDto findDtoById(Long seroId);

    ServiceOrders addServiceOrders(EnumCustomer.CreqType creqType, String arwgCode);
}
