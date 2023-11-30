package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderImpl implements ServOrderWorkorderService {

    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;

    @Override
    public ServiceOrderWorkorder addSoWorkorder(ServiceOrderWorkorder serviceOrderWorkorder) {
        ServiceOrderTasks seotById = soTasksRepository.findSeotById(1L);

        serviceOrderWorkorder = ServiceOrderWorkorder.builder()
                .sowoName(serviceOrderWorkorder.getSowoName())
                .sowoModDate(LocalDateTime.now())
                .sowoStatus(serviceOrderWorkorder.getSowoStatus())
                .serviceOrderTasks(seotById).build();

        log.info("SoOrderServiceImpl::addSoWorkorder created {} ", serviceOrderWorkorder);
        return soWorkorderRepository.save(serviceOrderWorkorder);
    }

    @Override
    public ServiceOrderWorkorder findBySowoId(Long sowoId) {
        ServiceOrderWorkorder sowoById = soWorkorderRepository.findBySowoId(sowoId);
        log.info("SoOrderServiceImpl::addSoWorkorder created {} ", sowoById.getSowoId());
        return sowoById;
    }
}
