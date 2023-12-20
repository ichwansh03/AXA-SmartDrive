package com.smartdrive.serviceorderservice.services.servorder;

import com.smartdrive.serviceorderservice.dto.response.ServiceOrderRespDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrders;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws Exception;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus);

   // int selectPartner(Partner partner, String seroId);

    int requestClosePolis(EnumModuleServiceOrders.SeroStatus seroStatus, String seroReason, String seroId);
}
