package com.smartdrive.serviceorderservice.services.servorder;


import com.smartdrive.serviceorderservice.entities.ServiceOrders;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws Exception;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    //int selectPartner(Partner partner, String seroId);

}
