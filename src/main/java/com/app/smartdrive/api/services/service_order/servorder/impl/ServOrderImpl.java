package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.service_order.*;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.*;
import com.app.smartdrive.api.services.service_order.servorder.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderImpl implements ServOrderService {

    private final SoOrderRepository soOrderRepository;

    @Transactional(readOnly = true)
    @Override
    public ServiceOrders findServiceOrdersById(String seroId) {
        ServiceOrders serviceOrdersById = soOrderRepository.findById(seroId)
                .orElseThrow(() -> new EntityNotFoundException("ID "+seroId+" is not found"));
        log.info("SoOrderServiceImpl::findServiceOrdersById in ID {} ",serviceOrdersById);
        return serviceOrdersById;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrders> findAllSeroByServId(Long servId) {
        List<ServiceOrders> allSeroByServId = soOrderRepository.findByServices_ServId(servId);

        if (allSeroByServId.isEmpty()){
            throw new EntityNotFoundException("findAllSeroByServId(Long servId)::Service ID is not found");
        }

        log.info("SoOrderServiceImpl::findAllSeroByServId from service ID {} ",servId);

        return allSeroByServId;
    }

    @Override
    public List<ServiceOrders> findAllSeroByUserId(Long custId) {
        List<ServiceOrders> entityId = soOrderRepository.findByServices_Users_UserEntityId(custId);

        if (entityId.isEmpty()) {
            throw new EntityNotFoundException("findAllSeroByUserId(Long custId)::custId is not found");
        }

        log.info("SoOrderServiceImpl::findAllSeroByServId from user ID {} ",custId);

        return entityId;
    }

    @Override
    public Page<ServiceOrderRespDto> pageServiceOrderByUserId(Pageable pageable, String seroOrdtType, String seroStatus) {

        EnumModuleServiceOrders.SeroStatus status = EnumModuleServiceOrders.SeroStatus.valueOf(seroStatus);
        Page<ServiceOrders> serviceOrdersPage;

        if (Objects.equals(seroOrdtType, "ALL")) {
            serviceOrdersPage = soOrderRepository.findAll(pageable);
        } else {
            EnumModuleServiceOrders.SeroOrdtType type = EnumModuleServiceOrders.SeroOrdtType.valueOf(seroOrdtType);
            serviceOrdersPage = soOrderRepository.findBySeroOrdtTypeAndSeroStatus(pageable, type, status);
        }

        return serviceOrdersPage.map(serviceOrders -> TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class));
    }

}
