package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.dto.service_order.ServicesDto;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SoOrderServiceImpl implements SoOrderService {

    private final SoOrderRepository soOrderRepository;
    private final UserRepository userRepository;
    private final SoRepository soRepository;
    private final ArwgRepository arwgRepository;
    @Override
    public ServicesDto findDtoById(String seroId) {
        return soOrderRepository.findByIdServicesDto(seroId);
    }

    @Override
    public ServiceOrders addServiceOrders(ServiceOrders serviceOrders, String seroId) {
        //Services services = soRepository.findById()
        AreaWorkGroup areaWorkGroup = arwgRepository.findByArwgCode("1");

        serviceOrders = ServiceOrders.builder()
                .seroSeroId(seroId)
                .seroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE)
                .seroStatus(EnumModuleServiceOrders.SeroStatus.OPEN)
                .seroReason("Reason")
                .servClaimNo("Claim")
                .servClaimStartdate(LocalDateTime.now())
                .servClaimEnddate(LocalDateTime.now().plusDays(30))
                .areaWorkGroup(areaWorkGroup).build();

        return soOrderRepository.save(serviceOrders);
    }
}
