package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.Exceptions.CheckPaymentException;
import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.EmailReq;
import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.ServicePremiCreditId;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.master.EmailService;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final SemiRepository semiRepository;
    private final SoRepository soRepository;
    private final EmailService emailService;

    private static final long PERIOD_IN_MILLIS = 24 * 60 * 60 * 1000;

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
    public boolean updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId) {
        ServicePremiCredit existSecr = secrRepository.findById(new ServicePremiCreditId(secrId, secrServId)).get();
        ServicePremi premi = semiRepository.findById(secrServId).get();
        Double premiMonthly = secrRepository.totalPremiMonthly();

        ServicePremiCredit newSecr = ServicePremiCredit.builder()
                .secrId(existSecr.getSecrId())
                .secrServId(existSecr.getSecrServId())
                .secrYear(existSecr.getSecrYear())
                .secrPremiCredit(secrReqDto.getSecrPremiCredit())
                .secrPremiDebet(secrReqDto.getSecrPremiDebet())
                .secrTrxDate(LocalDateTime.now())
                .secrDuedate(existSecr.getSecrDuedate()).build();

        if (checkDueDatePayment(newSecr)){
            semiRepository.updateSemiStatus(EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), existSecr.getSecrServId());
            throw new CheckPaymentException("your payment has passed deadline");
        }

        if (premi.getSemiPremiDebet() <= premiMonthly){
            semiRepository.updateSemiStatus(EnumModuleServiceOrders.SemiStatus.PAID.toString(), existSecr.getSecrServId());
            throw new CheckPaymentException("your payment has been paid");
        }

        log.info("ServPremiImpl::updateSecr successfully updated");
        secrRepository.save(newSecr);
        return true;
    }

    public boolean checkDueDatePayment(ServicePremiCredit servicePremiCredit) {
        servicePremiCredit = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        return servicePremiCredit.getSecrPremiDebet() == null
                && LocalDateTime.now().isAfter(servicePremiCredit.getSecrDuedate());
    }

    @Async
    @Scheduled(fixedRate = PERIOD_IN_MILLIS)
    public void sendNotify(){
        ServicePremiCredit existSecr = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        if (existSecr != null){
            Services services = soRepository.findById(existSecr.getSecrServId())
                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));
            if (existSecr.getSecrDuedate().minusDays(2).isBefore(LocalDateTime.now())){
                EmailReq emailReq = new EmailReq();
                emailReq.setTo(services.getUsers().getUserEmail());
                emailReq.setSubject("Due Date Premi Payment");
                emailReq.setBody("Pay your bill immediately before it closes");
                emailService.sendMail(emailReq);
            }
        }
    }
}
