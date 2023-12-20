package com.smartdrive.serviceorderservice.services.premi.impl;

import com.smartdrive.serviceorderservice.Exceptions.CheckPaymentException;
import com.smartdrive.serviceorderservice.dto.request.SecrReqDto;
import com.smartdrive.serviceorderservice.entities.ServicePremi;
import com.smartdrive.serviceorderservice.entities.ServicePremiCredit;
import com.smartdrive.serviceorderservice.entities.ServicePremiCreditId;
import com.smartdrive.serviceorderservice.entities.Services;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import com.smartdrive.serviceorderservice.repositories.SecrRepository;
import com.smartdrive.serviceorderservice.repositories.SemiRepository;
import com.smartdrive.serviceorderservice.repositories.SoRepository;
import com.smartdrive.serviceorderservice.services.premi.ServPremiCreditService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServPremiCreditImpl implements ServPremiCreditService {

    private final SecrRepository secrRepository;
    private final SemiRepository semiRepository;
    private final SoRepository soRepository;
    //private final EmailService emailService;

    private static final long PERIOD_IN_MILLIS = 24 * 60 * 60 * 1000;


    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public boolean updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId) {
        ServicePremiCredit existSecr = secrRepository.findById(new ServicePremiCreditId(secrId, secrServId))
                .orElseThrow(() -> new EntityNotFoundException("updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId)::ID is not found"));
        ServicePremi premi = semiRepository.findById(secrServId)
                .orElseThrow(() -> new EntityNotFoundException("findById(secrServId)::secrServId is not found"));

        ServicePremiCredit newSecr = ServicePremiCredit.builder()
                .secrId(existSecr.getSecrId())
                .secrServId(existSecr.getSecrServId())
                .secrYear(existSecr.getSecrYear())
                .secrPremiCredit(secrReqDto.getSecrPremiCredit())
                .secrPremiDebet(Optional.ofNullable(secrReqDto.getSecrPremiDebet()).orElse(BigDecimal.ZERO))
                .secrTrxDate(LocalDateTime.now())
                .secrDuedate(existSecr.getSecrDuedate())
                .services(premi.getServices()).build();

        secrRepository.save(newSecr);

        List<ServicePremiCredit> allCredits = secrRepository.findAll();
        BigDecimal countPremiMonthly = allCredits.stream()
                .map(ServicePremiCredit::getSecrPremiDebet)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (checkDueDatePayment(newSecr)){
            semiRepository.updateSemiStatus(EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), existSecr.getSecrServId());
            throw new CheckPaymentException("your payment has passed deadline");
        }

        if (premi.getSemiPremiDebet().compareTo(countPremiMonthly) >= 0){
            semiRepository.updateSemiStatus(EnumModuleServiceOrders.SemiStatus.PAID.toString(), existSecr.getSecrServId());
        }

        log.info("ServPremiImpl::updateSecr successfully updated");
        return true;
    }

    public boolean checkDueDatePayment(ServicePremiCredit servicePremiCredit) {
        servicePremiCredit = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        return servicePremiCredit.getSecrPremiDebet() == null
                && LocalDateTime.now().isAfter(servicePremiCredit.getSecrDuedate());
    }

//    @Async
//    @Scheduled(fixedRate = PERIOD_IN_MILLIS)
//    public void sendNotify(){
//        ServicePremiCredit existSecr = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
//        if (existSecr != null){
//            Services services = soRepository.findById(existSecr.getSecrServId())
//                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));
//            if (existSecr.getSecrDuedate().minusDays(2).isBefore(LocalDateTime.now())){
//                EmailReq emailReq = new EmailReq();
//                emailReq.setTo(services.getUsers().getUserEmail());
//                emailReq.setSubject("Due Date Premi Payment");
//                emailReq.setBody("Pay your bill immediately before it closes");
//                emailService.sendMail(emailReq);
//            }
//        }
//    }
}
