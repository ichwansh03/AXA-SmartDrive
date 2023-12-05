package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
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
    public List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();
        seot.add(new ServiceTaskReqDto("REVIEW & CHECK CUSTOMER REQUEST", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("PROSPEK CUSTOMER POTENTIAL", serviceOrders.getServices().getServStartDate().plusDays(2), serviceOrders.getServices().getServStartDate().plusDays(3), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("PREMI SCHEMA", serviceOrders.getServices().getServStartDate().plusDays(4), serviceOrders.getServices().getServStartDate().plusDays(5), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("LEGAL DOCUMENT SIGNED", serviceOrders.getServices().getServStartDate().plusDays(6), serviceOrders.getServices().getServStartDate().plusDays(7), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);

        List<ServiceOrderTasks> serviceOrderTasks = soTasksRepository.saveAll(mapperTaskList);

        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository);
        servOrderWorkorder.addSowoList(serviceOrderTasks);
        log.info("ServOrderTaskImpl::addFeasiblityList successfully saved {} ",serviceOrderTasks);
        return serviceOrderTasks;
    }

    @SneakyThrows({NoSuchMethodException.class, IllegalAccessException.class, InvocationTargetException.class})
    @Override
    public List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seotList = new ArrayList<>();

        Method generatePolis = SoAdapter.class.getMethod("generatePolisNumber", ServiceOrders.class);
        SoAdapter soAdapter = new SoAdapter();
        String polisNumber = (String) generatePolis.invoke(soAdapter, serviceOrders);

        seotList.add(new ServiceTaskReqDto("GENERATE POLIS NUMBER", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, polisNumber));
        seotList.add(new ServiceTaskReqDto("GENERATE PREMI", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seotList.add(new ServiceTaskReqDto("GENERATE VIRTUAL ACCOUNT", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seotList.add(new ServiceTaskReqDto("NOTIFY TO AGENT", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seotList.add(new ServiceTaskReqDto("NOTIFY TO CUSTOMER", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));

        log.info("ServOrderTaskImpl::addPolisList the result of number polis is {} ", seotList.get(0).getGeneratePolisTasks());

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seotList, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(mapperTaskList);
    }

    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders) {
        List<ServiceTaskReqDto> seot = new ArrayList<>();
        seot.add(new ServiceTaskReqDto("CHECK CUSTOMER PREMI", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("SETTLE CUSTOMER PREMI", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("CHECK VEHICLE CONDITION", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("CLAIM DOCUMENT APPROVED", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("NOTIFY PARTNER TO REPAIR", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("CALCULATE SPARE PART", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("NOTIFY CUSTOMER VEHICLE REPAIRED", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("NOTIFY AGENT CLAIM", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        seot.add(new ServiceTaskReqDto("FEEDBACK CUSTOMER", serviceOrders.getServices().getServStartDate(), serviceOrders.getServices().getServStartDate().plusDays(1), EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));

        List<ServiceOrderTasks> serviceOrderTasks = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(serviceOrderTasks);
    }

    @Override
    public List<ServiceOrderTasks> closeAllTasks(ServiceOrders serviceOrders) {

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
