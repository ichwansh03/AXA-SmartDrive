package com.app.smartdrive.api.services.service_order.servorder.orders;

import com.app.smartdrive.api.dto.service_order.response.FeasiblityDto;
import com.app.smartdrive.api.dto.service_order.response.PolisDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.services.service_order.servorder.BaseServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServOrderService extends BaseServiceOrder<ServiceOrderRespDto, String> {

    ServiceOrders findServiceOrdersById(String seroId);

    List<ServiceOrderRespDto> findAllOrderByServId(Long servId);

    List<ServiceOrders> findAllSeroByUserId(Long custId);

    Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus);

    void mapServiceOrderToDtoServices(Services services, ServiceRespDto serviceRespDto);
}
