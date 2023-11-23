package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SoTasksServiceImpl implements SoTasksService {

    private final SoTasksRepository soTasksRepository;
    private final SoOrderRepository soOrderRepository;
    private final ArwgRepository arwgRepository;

    @Override
    public Stream<ServiceOrderTasks> findAllBySeotSeroId(String seroId) {
        return soTasksRepository.findAllBySeotSeroId(seroId);
    }
}
