package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServPremiCreditImpl implements ServPremiCreditService {
    @Override
    public List<ServicePremiCredit> findAllBySecrServId(Long servId) {
        return null;
    }
}
