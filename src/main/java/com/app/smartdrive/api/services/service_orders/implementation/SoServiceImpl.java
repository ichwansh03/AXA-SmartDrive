package com.app.smartdrive.api.services.service_orders.implementation;

import com.app.smartdrive.api.entities.service_orders.ServiceOrders;
import com.app.smartdrive.api.repositories.service_order.SoRepository;
import com.app.smartdrive.api.services.service_orders.SoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoServiceImpl implements SoService {

    private final SoRepository soRepository;

    @Override
    public Optional<ServiceOrders> findSoById(String seroId) {
        return soRepository.findById(seroId);
    }
}
