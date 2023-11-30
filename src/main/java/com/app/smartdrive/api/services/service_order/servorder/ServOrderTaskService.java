package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;

public interface ServOrderTaskService {

    ServiceOrderTasks addServiceOrderTasks(ServiceOrderTasks serviceOrderTasks);

    ServiceOrderTasks findSeotById(Long seotId);

}
