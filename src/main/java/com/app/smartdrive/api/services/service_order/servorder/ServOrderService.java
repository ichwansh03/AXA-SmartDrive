package com.app.smartdrive.api.services.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServOrderService {

    ServiceOrders findServiceOrdersById(String seroId);

    ServiceOrderRespDto findOrderDtoById(String seroId);

    List<ServiceOrderRespDto> findAllOrderByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus);

}
