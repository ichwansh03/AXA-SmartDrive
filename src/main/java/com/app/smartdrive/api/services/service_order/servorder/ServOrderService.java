package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServOrderService {

    ServiceOrders addServiceOrders(Long servId) throws Exception;

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrders> findAllSeroByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus);

    int selectPartner(Partner partner, String seroId);

    int updateStatusRequest(EnumModuleServiceOrders.SeroStatus seroStatus, String seroReason, String seroId);
}
