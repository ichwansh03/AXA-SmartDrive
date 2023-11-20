package com.app.smartdrive.service_orders;

import com.app.smartdrive.service_orders.ServiceOrderTasks;

import java.util.List;

public interface SoTasksService {

    List<ServiceOrderTasks> findAllSoTasks();
}
