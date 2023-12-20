package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.Exceptions.ValidasiRequestException;
import com.app.smartdrive.api.dto.service_order.request.ServiceWorkorderReqDto;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.master.TemplateTaskWorkOrder;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderImpl implements ServOrderWorkorderService {

    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;
    private final TewoRepository tewoRepository;
    private final PartnerRepository partnerRepository;
    private final ArwgRepository arwgRepository;

    @Transactional
    @Override
    public List<ServiceOrderWorkorder> addSowoList(List<ServiceOrderTasks> seotList) {

        List<ServiceWorkorderReqDto> sowo = new ArrayList<>();
        List<TemplateTaskWorkOrder> taskWorkOrders = tewoRepository.findAll();
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(0).getTewoName(), LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(1).getTewoName(), LocalDateTime.now(), false, seotList.get(0)));
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(2).getTewoName(), LocalDateTime.now(), false, seotList.get(2)));
        sowo.add(new ServiceWorkorderReqDto(taskWorkOrders.get(3).getTewoName(), LocalDateTime.now(), false, seotList.get(3)));

        List<ServiceOrderWorkorder> workorders = TransactionMapper.mapListDtoToListEntity(sowo, ServiceOrderWorkorder.class);
        return soWorkorderRepository.saveAll(workorders);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId) {
        return soWorkorderRepository.findSowoBySeotId(seotId);
    }

    @Transactional
    @Override
    public int updateSowoStatus(Boolean sowoStatus, Long sowoId) {
        int updatedSowoStatus = soWorkorderRepository.updateSowoStatus(sowoStatus, LocalDateTime.now(), sowoId);

        if (updatedSowoStatus == 0) {
            throw new ValidasiRequestException("Failed to update data ",400);
        }

        log.info("SoOrderServiceImpl::addSoWorkorder updated in ID {} ", sowoId);
        return updatedSowoStatus;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAllWorkComplete(List<ServiceOrderWorkorder> sowoList) {
        boolean checkedAll = false;
        for (ServiceOrderWorkorder item : sowoList) {
            checkedAll = item.getSowoStatus();
        }
        return checkedAll;
    }

    @Override
    public List<ServiceOrderWorkorder> findAllByPartnerAndArwgCode(Long partnerId, String arwgCode) {
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(()->new EntityNotFoundException("Partner Not Found"));
        Specification<ServiceOrderWorkorder> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            Fetch<ServiceOrderWorkorder, ServiceOrderTasks> serviceOrderTasksFetch = root.fetch("serviceOrderTasks", JoinType.INNER);
            Join<ServiceOrderWorkorder, ServiceOrderTasks> serviceOrderTasksJoin = (Join<ServiceOrderWorkorder, ServiceOrderTasks>) serviceOrderTasksFetch;
            Fetch<ServiceOrderTasks, ServiceOrders> serviceOrdersFetch = serviceOrderTasksFetch.fetch("serviceOrders", JoinType.INNER);
            Join<ServiceOrderTasks, ServiceOrders> serviceOrdersJoin = (Join<ServiceOrderTasks, ServiceOrders>) serviceOrdersFetch;

            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(serviceOrderTasksJoin.get("seotName"), "NOTIFY PARTNER TO REPAIR"),
                            criteriaBuilder.equal(serviceOrdersJoin.get("partner"), partner)
                    )
            );

            if (Objects.nonNull(arwgCode)) {

                AreaWorkGroup areaWorkGroup = arwgRepository.findByArwgCode(arwgCode);
                if (areaWorkGroup != null) {
                    predicates.add(
                            criteriaBuilder.equal(serviceOrderTasksJoin.get("areaWorkGroup"), areaWorkGroup)
                    );
                }
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
        return soWorkorderRepository.findAll(specification);
    }

    @Transactional
    @Override
    public void createWorkorderTask(String sowoName, Long seotId){
        TemplateTaskWorkOrder workOrder = new TemplateTaskWorkOrder();
        workOrder.setTewoTestaId(seotId);
        workOrder.setTewoName(sowoName);
        tewoRepository.save(workOrder);

        ServiceOrderTasks tasks = soTasksRepository.findById(seotId)
                .orElseThrow(() -> new EntityNotFoundException("::createWorkorderTask ID is not found"));

        ServiceOrderWorkorder soWorkorder = new ServiceOrderWorkorder();
        soWorkorder.setSowoName(workOrder.getTewoName());
        soWorkorder.setServiceOrderTasks(tasks);
        soWorkorder.setSowoStatus(false);
        soWorkorder.setSowoModDate(LocalDateTime.now());

        soWorkorderRepository.save(soWorkorder);
    }
}
