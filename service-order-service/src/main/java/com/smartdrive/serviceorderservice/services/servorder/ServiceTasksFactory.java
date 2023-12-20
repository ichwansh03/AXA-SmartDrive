package com.smartdrive.serviceorderservice.services.servorder;


import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.entities.ServiceOrders;

import java.util.List;

public interface ServiceTasksFactory {

    List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders);

    List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) throws Exception;

    List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders);

}
