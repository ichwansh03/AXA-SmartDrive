package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.SoWorkorderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoWorkorderImpl implements SoWorkorderService {

    private final SoWorkorderRepository soWorkorderRepository;

    @Override
    public List<ServiceOrderWorkorder> findAllBySowoSeotId(Long seotId) {
        return soWorkorderRepository.findAllBySowoSeotId(seotId);
    }
}
