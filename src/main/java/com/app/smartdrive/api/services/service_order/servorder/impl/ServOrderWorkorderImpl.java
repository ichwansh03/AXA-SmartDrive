package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.repositories.master.ArwgRepository;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderWorkorderService;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderWorkorderImpl implements ServOrderWorkorderService {

    private final SoWorkorderRepository soWorkorderRepository;
    private final PartnerRepository partnerRepository;
    private final ArwgRepository arwgRepository;

    @Override
    public List<ServiceOrderWorkorder> findSowoBySeotId(Long seotId) {
        return soWorkorderRepository.findSowoBySeotId(seotId);
    }

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
}
