package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.SoWorkorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SoWorkorderImpl implements SoWorkorderService {

    private final SoWorkorderRepository soWorkorderRepository;
    private final SoTasksRepository soTasksRepository;

    @Override
    public Stream<ServiceOrderWorkorder> findAllBySowoSeotId(Long seotId) {
        return soWorkorderRepository.findAllBySowoSeotId(seotId);
    }

    @Override
    public ServiceOrderWorkorder addSowoBySeotId(ServiceOrderWorkorder sowo) {
        
        return null;
    }
}
