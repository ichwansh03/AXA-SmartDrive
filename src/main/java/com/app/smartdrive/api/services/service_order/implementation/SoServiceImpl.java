package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.SoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;

    @Override
    public Optional<Services> findServicesById(Long servId) {
        return soRepository.findById(servId);
    }

    @Override
    public Services addServices(Services services) {
        CustomerRequest customerRequest = new CustomerRequest();
        Employees employees = new Employees();

        services.setServCreatedOn(LocalDate.now());
        services.setServType(EnumModuleServiceOrders.ServType.OPEN.label);
        services.setServInsuranceNo("");
        services.setServVehicleNumber("");
        services.setServStartDate(LocalDate.now());
        services.setServEndDate(LocalDate.now().plusYears(1));
        services.setServStatus(EnumModuleServiceOrders.ServStatus.ACTIVE);
        services.setServServId(services.getServId());
        services.setServCustEntityId(employees.getEmpEntityid());
        services.setServCreqEntityId(customerRequest.getCreqEntityId());

        return services;
    }
}
