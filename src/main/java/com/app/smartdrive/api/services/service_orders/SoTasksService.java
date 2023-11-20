package com.app.smartdrive.api.services.service_orders;

import com.app.smartdrive.api.entities.service_orders.ServiceOrderTasks;

import java.util.List;
import java.util.Set;

public interface SoTasksService {

    List<ServiceOrderTasks> findAllSoTasks();
}
