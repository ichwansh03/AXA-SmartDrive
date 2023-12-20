package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServiceOrderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOrderFactoryImpl implements ServiceOrderFactory {

    private final SoOrderRepository soOrderRepository;

    SoAdapter soAdapter = new SoAdapter();

    @Transactional
    @Override
    public ServiceOrders generateSeroFeasiblity(Services services){

        ServiceOrders serviceOrders = buildCommonSeroData(services, null, null, null);
        ServiceOrders saved = soOrderRepository.save(serviceOrders);

        log.info("ServOrderTaskImpl::generateSeroFeasiblity successfully added in ID {} ", saved.getSeroId());

        return saved;
    }

    @Transactional
    @Override
    public ServiceOrders handlePolisAndClaim(Services services, LocalDateTime startDate, LocalDateTime endDate, String prefixSeroId){

        ServiceOrders existingSero = soOrderRepository.findBySeroIdLikeAndServices_ServId(prefixSeroId, services.getServId());
        ServiceOrders serviceOrders = buildCommonSeroData(services, existingSero, startDate, endDate);

        ServiceOrders saved = soOrderRepository.save(serviceOrders);
        log.info("ServOrderTaskImpl::generateSeroClaim successfully added {} ", saved.getSeroId());

        return saved;
    }

    private ServiceOrders buildCommonSeroData(Services services, ServiceOrders parentSero, LocalDateTime startDate, LocalDateTime endDate){
        String formatSeroId = soAdapter.formatServiceOrderId(services);

        return ServiceOrders.builder()
                .seroId(formatSeroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(EnumModuleServiceOrders.SeroStatus.OPEN)
                .servClaimNo(services.getServInsuranceNo())
                .servClaimStartdate(startDate)
                .servClaimEnddate(endDate)
                .parentServiceOrders(parentSero)
                .seroAgentEntityid(services.getCustomer().getEmployeeAreaWorkgroup().getEawgId())
                .employees(services.getCustomer().getEmployeeAreaWorkgroup())
                .services(services).build();

    }
}
