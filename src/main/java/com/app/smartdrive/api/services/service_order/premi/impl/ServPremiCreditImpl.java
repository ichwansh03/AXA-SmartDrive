package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.ServicePremiCreditId;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
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

    private final SecrRepository secrRepository;

    @Override
    public List<ServicePremiCredit> findByServId(Long servId) {
        return secrRepository.findByServices_ServId(servId);
    }

    @Transactional
    @Override
    public List<ServicePremiCredit> addSecr(ServicePremi servicePremi) {

        LocalDateTime semiModifiedDate = servicePremi.getSemiModifiedDate();
        List<ServicePremiCredit> servicePremiCredits = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            ServicePremiCredit servicePremiCredit = new ServicePremiCredit();
            LocalDateTime dateTime = semiModifiedDate.plusMonths(i);

            servicePremiCredit.setSecrServId(servicePremi.getSemiServId());
            servicePremiCredit.setSecrYear(String.valueOf(dateTime.getYear()));
            servicePremiCredit.setSecrDuedate(dateTime);
            servicePremiCredit.setServices(servicePremi.getServices());

            ServicePremiCredit save = secrRepository.save(servicePremiCredit);
            servicePremiCredits.add(save);
        }
        log.info("ServPremiImpl::addSecr successfully added service premi credit");
        return servicePremiCredits;
    }

    @Override
    public ServicePremiCredit findByDueDate() {
        return secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
    }

    @Override
    public SecrReqDto updateSecr(SecrReqDto secrReqDto) {
        ServicePremiCredit existSecr = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));

        ServicePremiCredit build = ServicePremiCredit.builder()
                .secrId(existSecr.getSecrId())
                .secrServId(existSecr.getSecrServId())
                .secrYear(existSecr.getSecrYear())
                .secrPremiCredit(secrReqDto.getSecrPremiCredit())
                .secrPremiDebet(secrReqDto.getSecrPremiDebet())
                .secrTrxDate(LocalDateTime.now())
                .secrDuedate(existSecr.getSecrDuedate()).build();

        ServicePremiCredit save = secrRepository.save(build);
        log.info("ServPremiImpl::updateSecr successfully updated");
        return TransactionMapper.mapEntityToDto(save, SecrReqDto.class);
    }

}
