package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.dto.service_order.request.ServiceTasksReqDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SoTasksRepository;
import com.app.smartdrive.api.repositories.service_orders.SoWorkorderRepository;
import com.app.smartdrive.api.services.service_order.SoAdapter;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServOrderTaskImpl implements ServOrderTaskService {

    private final SoTasksRepository soTasksRepository;
    private final SoWorkorderRepository soWorkorderRepository;

    @Transactional
    @Override
    public List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders, Services services) {

        List<ServiceOrderTasks> seot = new ArrayList<>();
        seot.add(new ServiceOrderTasks("REVIEW & CHECK CUSTOMER REQUEST", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("PROSPEK CUSTOMER POTENTIAL", services.getServStartDate().plusDays(2), services.getServStartDate().plusDays(3), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("PREMI SCHEMA", services.getServStartDate().plusDays(4), services.getServStartDate().plusDays(5), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("LEGAL DOCUMENT SIGNED", services.getServStartDate().plusDays(6), services.getServStartDate().plusDays(7), serviceOrders.getAreaWorkGroup(), serviceOrders));

        List<ServiceOrderTasks> tasksList = soTasksRepository.saveAll(seot);

        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository);
        servOrderWorkorder.addSowoList(seot);

        return tasksList;
    }

    @SneakyThrows({NoSuchMethodException.class, IllegalAccessException.class, InvocationTargetException.class})
    @Override
    public List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders, Services services) {

//        List<ServiceOrderTasks> seot = new ArrayList<>();
//        seot.add(new ServiceOrderTasks("GENERATE POLIS NUMBER", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
//        seot.add(new ServiceOrderTasks("GENERATE PREMI", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
//        seot.add(new ServiceOrderTasks("GENERATE VIRTUAL ACCOUNT", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
//        seot.add(new ServiceOrderTasks("NOTIFY TO AGENT", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
//        seot.add(new ServiceOrderTasks("NOTIFY TO CUSTOMER", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));

        List<ServiceTasksReqDto> seotListDto = new ArrayList<>();

        Method generatePolis = SoAdapter.class.getMethod("generatePolis", ServiceOrders.class);
        SoAdapter soAdapter = new SoAdapter();
        String polisNumber = (String) generatePolis.invoke(soAdapter, serviceOrders);

        seotListDto.add(new ServiceTasksReqDto("GENERATE POLIS NUMBER", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders, serviceOrders.getAreaWorkGroup(), polisNumber));
        seotListDto.add(new ServiceTasksReqDto("GENERATE PREMI", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders, serviceOrders.getAreaWorkGroup(), polisNumber));
        seotListDto.add(new ServiceTasksReqDto("GENERATE VIRTUAL ACCOUNT", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders, serviceOrders.getAreaWorkGroup(), polisNumber));
        seotListDto.add(new ServiceTasksReqDto("NOTIFY TO AGENT", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders, serviceOrders.getAreaWorkGroup(), polisNumber));
        seotListDto.add(new ServiceTasksReqDto("NOTIFY TO CUSTOMER", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders, serviceOrders.getAreaWorkGroup(), polisNumber));

        List<ServiceOrderTasks> seotList = TransactionMapper.mapListDtoToListEntity(seotListDto, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(seotList);
    }

    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders, Services services) {
        List<ServiceOrderTasks> seot = new ArrayList<>();
        seot.add(new ServiceOrderTasks("CHECK CUSTOMER PREMI", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("SETTLE CUSTOMER PREMI", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("CHECK VEHICLE CONDITION", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("CLAIM DOCUMENT APPROVED", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("NOTIFY PARTNER TO REPAIR", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("CALCULATE SPARE PART", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("NOTIFY CUSTOMER VEHICLE REPAIRED", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("NOTIFY AGENT CLAIM", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        seot.add(new ServiceOrderTasks("FEEDBACK CUSTOMER", services.getServStartDate(), services.getServStartDate().plusDays(1), serviceOrders.getAreaWorkGroup(), serviceOrders));
        return soTasksRepository.saveAll(seot);
    }

    @Override
    public List<ServiceOrderTasks> closeAllTasks(ServiceOrders serviceOrders, Services services) {

        return null;
    }

    @Override
    public List<ServiceOrderTasks> checkAllTaskComplete(List<ServiceOrderTasks> seotList, ServiceOrderTasks seot) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ServiceOrderTasks> findSeotBySeroId(String seroId) {
        List<ServiceOrderTasks> seotBySeroId = soTasksRepository.findSeotBySeroId(seroId);
        log.info("SoOrderServiceImpl::findSeotById in ID start from {} ", seotBySeroId.get(0));
        return seotBySeroId;
    }

    @Transactional
    @Override
    public int updateTasksStatus(String seotStatus, Long seotId) {
        int serviceOrderTasks = soTasksRepository.updateTasksStatus(seotStatus, seotId);
        log.info("SoOrderServiceImpl::findSeotById updated in ID {} ",seotId);
        return serviceOrderTasks;
    }

}
