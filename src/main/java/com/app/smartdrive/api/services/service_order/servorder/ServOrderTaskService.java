package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ServOrderTaskService {

    List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders, Services services) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> closeAllTasks(ServiceOrders serviceOrders, Services services);

    List<ServiceOrderTasks> checkAllTaskComplete(List<ServiceOrderTasks> seotList, ServiceOrderTasks seot);

    List<ServiceOrderTasks> findSeotBySeroId(String seroId);

    int updateTasksStatus(String seotStatus, Long seotId);

}
