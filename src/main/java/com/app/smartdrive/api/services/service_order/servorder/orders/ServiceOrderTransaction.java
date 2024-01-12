package com.app.smartdrive.api.services.service_order.servorder.orders;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;

import java.time.LocalDateTime;

public interface ServiceOrderTransaction {

    ServiceOrders addServiceOrders(Long servId);

    ServiceOrders generateSeroFeasiblity(Services services);

    ServiceOrders handlePolisAndClaim(Services services, LocalDateTime startDate, LocalDateTime endDate, String prefixSeroId);

    ServiceOrders buildCommonSeroData(Services services, ServiceOrders parentSero, LocalDateTime startDate, LocalDateTime endDate);

    void closeExistingSero(ServiceOrders existingSero);

    void requestCloseAllSero(Services services);

    int updateStatusRequest(EnumModuleServiceOrders.SeroStatus seroStatus, String seroReason, String seroId);

    int selectPartner(Partner partner, String seroId);

}
