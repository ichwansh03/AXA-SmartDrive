package com.smartdrive.serviceorderservice.services.servorder.impl;

import com.smartdrive.serviceorderservice.Exceptions.EntityNotFoundException;
import com.smartdrive.serviceorderservice.Exceptions.ValidasiRequestException;
import com.smartdrive.serviceorderservice.dto.request.SeotPartnerDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.entities.ServiceOrderWorkorder;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import com.smartdrive.serviceorderservice.repositories.SoOrderRepository;
import com.smartdrive.serviceorderservice.repositories.SoTasksRepository;
import com.smartdrive.serviceorderservice.repositories.SoWorkorderRepository;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderTaskService;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderWorkorderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoOrderRepository soOrderRepository;
    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
//    private final PartnerRepository partnerRepository;
//
//    private final EmailService emailService;
    private final ServOrderWorkorderService servOrderWorkorderService;

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderTasks> findSeotBySeroId(String seroId) {
        List<ServiceOrderTasks> orderTasks = soTasksRepository.findByServiceOrders_SeroId(seroId);

        if (orderTasks.isEmpty()) {
            throw new EntityNotFoundException("ID "+seroId+" is not found");
        }

        log.info("SoOrderServiceImpl::findSeotBySeroId in ID {} ",seroId);

        return orderTasks;
    }

    @Transactional
    @Override
    public int updateTasksStatus(EnumModuleServiceOrders.SeotStatus seotStatus, Long seotId) {
        int updateSeot = soTasksRepository.updateTasksStatus(seotStatus, seotId);

        if (updateSeot == 0) {
            throw new ValidasiRequestException("Failed to update data",400);
        }
        log.info("SoOrderServiceImpl::findSeotById updated in ID {} ",seotId);
        return updateSeot;
    }

    @Transactional
    @Override
    public SeotPartnerDto updateSeotPartner(SeotPartnerDto seotPartnerDto, Long seotId) {
        ServiceOrderTasks orderTasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("::updateSeotPartner() ID "+seotId+" is not found"));
        SeotPartnerDto seotPartner = SeotPartnerDto.builder()
                .partnerId(seotPartnerDto.getPartnerId())
                .repair(seotPartnerDto.getRepair())
                .sparepart(seotPartnerDto.getSparepart())
                .seotStatus(seotPartnerDto.getSeotStatus()).build();

        if (seotPartnerDto.getRepair()) {
            servOrderWorkorderService.createWorkorderTask("REPAIR", seotId);
            log.info("SoOrderServiceImpl::updateSeotPartner add REPAIR to workorder");
        }

        if (seotPartnerDto.getSparepart()) {
            servOrderWorkorderService.createWorkorderTask("GANTI SUKU CADANG", seotId);
            log.info("SoOrderServiceImpl::updateSeotPartner add GANTI SUKU CADANG to workorder");
        }

//        Partner partner = partnerRepository.findById(seotPartner.getPartnerId())
//                .orElseThrow(() -> new EntityNotFoundException("::partnerRepository.findById ID "+seotPartner.getPartnerId()+" is not found"));
//        soOrderRepository.selectPartner(partner, orderTasks.getServiceOrders().getSeroId());

        return seotPartner;
    }

//    @Override
//    public void notifyTask(EmailReq emailReq, ServiceOrders serviceOrders, String subject, String message){
//        emailReq.setTo(serviceOrders.getEmployees().getEmployees().getUser().getUserEmail());
//        emailReq.setSubject(subject);
//        emailReq.setBody(message);
//        log.info("Email has been send");
//        emailService.sendMail(emailReq);
//    }

    @Override
    public boolean checkAllTaskComplete(String seroId) {

        List<ServiceOrderTasks> seotBySeroId = soTasksRepository.findByServiceOrders_SeroId(seroId);

        List<ServiceOrderWorkorder> sowoBySeotId = soWorkorderRepository.findSowoBySeotId(seotBySeroId.get(0).getSeotId());
        boolean allWorkComplete = servOrderWorkorderService.checkAllWorkComplete(sowoBySeotId);

        boolean checkedAll = false;
        for (ServiceOrderTasks item : seotBySeroId) {
            if (item.getSeotStatus().toString().equals("COMPLETED") && allWorkComplete){
                checkedAll = true;
            }
        }

        log.info("ServOrderTaskImpl::checkAllTaskComplete the results is {}",checkedAll);
        return checkedAll;
    }
}
