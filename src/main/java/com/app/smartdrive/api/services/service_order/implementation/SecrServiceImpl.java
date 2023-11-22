package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.services.service_order.SecrService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecrServiceImpl implements SecrService {

    private final SecrRepository secrRepository;

    @Override
    public List<ServicePremiCredit> findAllBySecrServId(Long servId) {
        return secrRepository.findAllBySecrServId(servId);
    }
}
