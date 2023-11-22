package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.dto.ServicesDto;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoOrderServiceImpl implements SoOrderService {

    private final SoOrderRepository soRepository;

    @Override
    public ServiceOrders getById(String seroId) {
        return soRepository.findById(seroId).get();
    }

    @Override
    public List<ServiceOrders> getAll() {
        return soRepository.findAll();
    }

    @Override
    public ServiceOrders save(ServiceOrders entity) {
        return soRepository.save(entity);
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public ServicesDto findDtoById(String seroId) {
        return soRepository.findByIdWithServicesAndServiceOrdersAndEmployeesAndUser(seroId);
    }
}
