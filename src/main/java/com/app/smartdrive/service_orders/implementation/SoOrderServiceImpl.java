package com.app.smartdrive.service_orders.implementation;

import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.service_orders.ServiceOrders;
import com.app.smartdrive.service_orders.Services;
import com.app.smartdrive.service_orders.SoOrderService;
import com.app.smartdrive.service_orders.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.service_order.SoOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoOrderServiceImpl implements SoOrderService {

    private final SoOrderRepository soRepository;

    @Override
    public Optional<ServiceOrders> findBySeroId(String seroId) {
        return soRepository.findById(seroId);
    }

    @Override
    public ServiceOrders addSero(ServiceOrders serviceOrders) {

        Services services = new Services();
        Employees employees = new Employees();
        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String seroIdPolis = "PL-"+String.format("%04d", serviceOrders.getSeroId())+services.getServCreatedOn().format(formatter);
        String seroIdClaim = "CL-"+String.format("%04d", serviceOrders.getSeroId())+services.getServCreatedOn().format(formatter);
        String seroIdTutup = "TP-"+String.format("%04d", serviceOrders.getSeroId())+services.getServCreatedOn().format(formatter);

        serviceOrders.setSeroId(seroIdPolis);
        serviceOrders.setSeroOrdtType(EnumModuleServiceOrders.SeroOrdtType.CREATE);
        serviceOrders.setSeroStatus(EnumModuleServiceOrders.SeroStatus.OPEN);
        serviceOrders.setSeroReason("Test");
        serviceOrders.setServClaimNo("oo");
        serviceOrders.setServClaimStartdate(services.getServStartDate());
        serviceOrders.setServClaimEnddate(services.getServEndDate());
        serviceOrders.setSeroServId(services.getServId());
        serviceOrders.setSeroSeroId(serviceOrders.getSeroId());
        serviceOrders.setSeroAgentEntityid(employees.getEmpEntityid());
        serviceOrders.setSeroArwgCode(areaWorkGroup.getArwgCode());

        return serviceOrders;
    }
}
