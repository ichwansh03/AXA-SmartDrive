package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.dto.service_order.ServicesDto;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;

import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SoOrderServiceImpl implements SoOrderService {

    private final SoOrderRepository soOrderRepository;

    @Override
    public ServicesDto findDtoById(Long seroId) {
        return soOrderRepository.findByIdServicesDto(seroId);
    }

    @Override
     public ServiceOrders addServiceOrders(EnumCustomer.CreqType creqType, String arwgCode) {

        SoAdapter soAdapter = new SoAdapter();
        String formatSeroId = soAdapter.formatServiceOrderId(creqType, 1L, LocalDateTime.now());

        ServiceOrders serviceOrders = ServiceOrders.builder()
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(EnumModuleServiceOrders.SeroStatus.OPEN)
                .seroReason("Reason")
                .servClaimNo("Claim")
                .servClaimStartdate(LocalDateTime.now())
                .servClaimEnddate(LocalDateTime.now().plusDays(30))
                .seroArwgCode(arwgCode).build();

        return soOrderRepository.save(serviceOrders);
    }
}
