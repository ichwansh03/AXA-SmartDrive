package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> generateSeotFeasiblity(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> generateSeotPolis(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> generateSeotClaim(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> closeAllTasks(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> checkAllTaskComplete(List<ServiceOrderTasks> seotList, ServiceOrderTasks seot);

    List<ServiceOrderTasks> findSeotBySeroId(String seroId);

    int updateTasksStatus(String seotStatus, Long seotId);

}
