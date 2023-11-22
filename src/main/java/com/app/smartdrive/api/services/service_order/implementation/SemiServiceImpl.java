package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.services.service_order.SemiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemiServiceImpl implements SemiService {

    private final SemiRepository semiRepository;

    @Override
    public List<ServicePremi> findAllBySemiServId(Long servId) {
        return semiRepository.findAllBySemiServId(servId);
    }
}
