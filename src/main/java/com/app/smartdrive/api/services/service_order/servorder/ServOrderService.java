package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.ServiceOrderReqDto;
import com.app.smartdrive.api.dto.service_order.request.ServiceReqDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws Exception;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);

    boolean checkAllTaskComplete(String seroId);

}
