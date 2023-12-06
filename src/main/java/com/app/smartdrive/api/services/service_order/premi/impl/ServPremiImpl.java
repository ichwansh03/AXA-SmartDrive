package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServPremiImpl implements ServPremiService {

    private final SemiRepository semiRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ServicePremi> findAllBySemiServId(Long servId) {
        List<ServicePremi> allBySemiServId = semiRepository.findAllBySemiServId(servId);
        log.info("ServPremiImpl::findAllBySemiServId successfully viewed");
        return allBySemiServId;
    }

    @Transactional
    @Override
    public ServicePremi addSemi(ServicePremi servicePremi) {


        return null;
    }
}
