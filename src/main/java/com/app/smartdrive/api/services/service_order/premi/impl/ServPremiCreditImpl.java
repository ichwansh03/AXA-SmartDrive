package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServPremiCreditImpl implements ServPremiCreditService {

    private final SemiRepository semiRepository;
    private final SecrRepository secrRepository;
    private final SoRepository soRepository;

    @Override
    public List<ServicePremiCredit> findAllBySecrServId(Long servId) {
        return null;
    }

    @Transactional
    @Override
    public List<ServicePremiCredit> addSecr(ServicePremi servicePremi) {

        ServicePremi premi = semiRepository.findById(1L).get();
        Services services = soRepository.findById(1L).get();
        LocalDateTime semiModifiedDate = premi.getSemiModifiedDate();
        List<ServicePremiCredit> servicePremiCredits = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            ServicePremiCredit servicePremiCredit = new ServicePremiCredit();
            LocalDateTime dateTime = semiModifiedDate.plusMonths(i);

            servicePremiCredit.setSecrServId(services.getServId());
            servicePremiCredit.setSecrYear(String.valueOf(dateTime.getYear()));
            servicePremiCredit.setSecrDuedate(dateTime);
            //servicePremiCredit.setServices(services);

            ServicePremiCredit save = secrRepository.save(servicePremiCredit);
            servicePremiCredits.add(save);
            //secrRepository.flush();
        }

        return servicePremiCredits;
    }


}
