package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderImpl implements ServOrderWorkorderService {

    private final SoWorkorderRepository soWorkorderRepository;

    @Override
    public List<ServiceOrderWorkorder> generateSowo(List<ServiceOrderTasks> seotList) {
        List<ServiceOrderWorkorder> sowo = new ArrayList<>();
        sowo.add(new ServiceOrderWorkorder("CHECK UMUR", LocalDateTime.now(), false, 1, seotList.get(0)));
        sowo.add(new ServiceOrderWorkorder("RELATE GOVERNMENT", LocalDateTime.now(), false, 1, seotList.get(0)));
        sowo.add(new ServiceOrderWorkorder("PREMI SCHEMA", LocalDateTime.now(), false, 3, seotList.get(2)));
        sowo.add(new ServiceOrderWorkorder("LEGAL DOCUMENT SIGNED", LocalDateTime.now(), false, 3, seotList.get(2)));
        return soWorkorderRepository.saveAll(sowo);
    }

    @Override
    public ServiceOrderWorkorder findBySowoId(Long sowoId) {
        ServiceOrderWorkorder sowoById = soWorkorderRepository.findBySowoId(sowoId);
        log.info("SoOrderServiceImpl::addSoWorkorder created {} ", sowoById.getSowoId());
        return sowoById;
    }
}
