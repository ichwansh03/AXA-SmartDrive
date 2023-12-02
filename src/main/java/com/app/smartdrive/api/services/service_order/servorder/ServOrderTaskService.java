package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> generateSeotFeasiblity(ServiceOrders serviceOrders, Services services);

    ServiceOrderTasks findSeotById(Long seotId);

}
