package com.app.smartdrive.api.services.service_orders.implementation;

import com.app.smartdrive.api.entities.service_orders.ServiceOrderTasks;
import com.app.smartdrive.api.repositories.service_order.SoTasksRepository;
import com.app.smartdrive.api.services.service_orders.SoTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoTasksServiceImpl implements SoTasksService {

    private final SoTasksRepository soTasksRepository;

    @Override
    public List<ServiceOrderTasks> findAllSoTasks() {
        return soTasksRepository.findAll();
    }
}
