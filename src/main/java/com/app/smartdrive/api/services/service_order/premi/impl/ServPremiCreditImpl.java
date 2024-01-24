package com.app.smartdrive.api.services.service_order.premi.impl;

import com.app.smartdrive.api.Exceptions.CheckPaymentException;
import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.dto.service_order.response.SecrDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import com.app.smartdrive.api.entities.service_order.ServicePremiCreditId;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SecrRepository;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoRepository;
import com.app.smartdrive.api.services.service_order.premi.ServPremiCreditService;
import com.app.smartdrive.api.services.service_order.servorder.tasks.ServOrderTaskService;
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
    private final ServOrderTaskService servOrderTaskService;

    @Override
    public List<SecrDto> findPremiCreditByServId(Long servId) {
        Optional<ServicePremi> semiById = semiRepository.findById(servId);
        return semiById.stream()
                .map(secr -> TransactionMapper.mapEntityToDto(secr, SecrDto.class))
                .toList();
    }

    @Transactional
    @Override
    public List<ServicePremiCredit> addSecr(ServicePremi servicePremi) {

        LocalDateTime semiModifiedDate = servicePremi.getSemiModifiedDate();
        List<ServicePremiCredit> servicePremiCredits = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            ServicePremiCredit save = generatePremiData(servicePremi, semiModifiedDate.plusMonths(i));
            servicePremiCredits.add(save);
        }
        log.info("ServPremiImpl::addSecr successfully added service premi credit");
        return servicePremiCredits;
    }

    @Override
    public ServicePremiCredit addSecrCash(ServicePremi servicePremi) {
        return generatePremiData(servicePremi, LocalDateTime.now().plusYears(1));
    }

    @Transactional
    @Override
    public boolean updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId) {
        ServicePremiCredit existSecr = secrRepository.findById(new ServicePremiCreditId(secrId, secrServId))
                .orElseThrow(() -> new EntityNotFoundException("updateSecr(SecrReqDto secrReqDto, Long secrId, Long secrServId)::ID is not found"));
        ServicePremi premi = semiRepository.findById(secrServId)
                .orElseThrow(() -> new EntityNotFoundException("findById(secrServId)::secrServId is not found"));

        ServicePremiCredit premiCredit = updateSecrData(premi, existSecr, secrReqDto);

        secrRepository.save(premiCredit);

        if (checkDueDatePayment()){
            throw new CheckPaymentException("your payment has passed deadline");
        }

        List<ServicePremiCredit> allCredits = secrRepository.findAll();
        BigDecimal countPremiMonthly = allCredits.stream()
                .map(ServicePremiCredit::getSecrPremiDebet)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (premi.getSemiPremiDebet().compareTo(countPremiMonthly) >= 0){
            semiRepository.updateSemiStatus(EnumModuleServiceOrders.SemiStatus.PAID.toString(), existSecr.getSecrServId());
        }

        log.info("ServPremiImpl::updateSecr successfully updated");
        return true;
    }

    public boolean checkDueDatePayment() {
        ServicePremiCredit servicePremiCredit = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        boolean check = false;
        if (servicePremiCredit.getSecrPremiDebet() == null
                && LocalDateTime.now().isAfter(servicePremiCredit.getSecrDuedate())){
            semiRepository.updateSemiStatus(EnumModuleServiceOrders.SemiStatus.INACTIVE.toString(), servicePremiCredit.getSecrServId());
            check = true;
        }
        return check;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void sendNotify(){
        ServicePremiCredit existSecr = secrRepository.findBySecrDuedateBetween(LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
        if (existSecr != null){
            Services services = soRepository.findById(existSecr.getSecrServId())
                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));
            try {
                //sent email before D-2 due date
                LocalDateTime minusDays = existSecr.getSecrDuedate().minusDays(2);
                if (minusDays.isBefore(LocalDateTime.now())){
                    servOrderTaskService.notifyTask(services.getUsers().getUserEmail(), "D-2 Due Date Premi Payment", "Pay your bill immediately before it closes");
                    log.info("Email sent successfully");
                }

                if (checkDueDatePayment()){
                    servOrderTaskService.notifyTask(services.getUsers().getUserEmail(), "Premi is Inactive", "your premi has inactive");
                }

            } catch (Exception e){
                log.error("Error during scheduled task exception ",e);
            }
        }
    }

    private ServicePremiCredit updateSecrData(ServicePremi semi, ServicePremiCredit secr, SecrReqDto secrReqDto){
        return ServicePremiCredit.builder()
                .secrId(secr.getSecrId())
                .secrServId(secr.getSecrServId())
                .secrYear(secr.getSecrYear())
                .secrPremiCredit(secrReqDto.getSecrPremiCredit())
                .secrPremiDebet(Optional.ofNullable(secrReqDto.getSecrPremiDebet()).orElse(BigDecimal.ZERO))
                .secrTrxDate(LocalDateTime.now())
                .secrDuedate(secr.getSecrDuedate())
                .services(semi.getServices()).build();
    }

    private ServicePremiCredit generatePremiData(ServicePremi servicePremi, LocalDateTime dateTime){
        ServicePremiCredit servicePremiCredit = new ServicePremiCredit();

        servicePremiCredit.setSecrServId(servicePremi.getSemiServId());
        servicePremiCredit.setSecrYear(String.valueOf(dateTime.getYear()));
        servicePremiCredit.setSecrDuedate(dateTime);
        servicePremiCredit.setServices(servicePremi.getServices());

        return secrRepository.save(servicePremiCredit);
    }
}
