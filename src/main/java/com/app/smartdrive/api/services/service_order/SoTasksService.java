package com.app.smartdrive.api.services.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;

import java.util.List;

public interface SoTasksService {

    List<ServiceOrderTasks> findAllSoTasks();
}
