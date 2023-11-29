package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.User;

public interface ServOrderService {

    Services addServices(CustomerRequest customerRequest,
                         CustomerInscAssets customerInscAssets,
                         User user,
                         Long creqId);

    Services findServicesById(Long servId);

    ServiceOrders addServiceOrders(ServiceOrders serviceOrders);

    ServiceOrders findServiceOrdersById(String seroId);

    ServiceOrderTasks addServiceOrderTasks(ServiceOrderTasks serviceOrderTasks);

    ServiceOrderTasks findSeotById(Long seotId);

    ServiceOrderWorkorder addSoWorkorder(ServiceOrderWorkorder serviceOrderWorkorder);
}
