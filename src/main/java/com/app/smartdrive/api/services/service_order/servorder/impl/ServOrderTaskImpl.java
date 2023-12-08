package com.app.smartdrive.api.services.service_order.servorder.impl;

import com.app.smartdrive.api.dto.service_order.request.ServiceTaskReqDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.TemplateServiceTask;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.enumerated.EnumModuleServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.master.TestaRepository;
import com.app.smartdrive.api.repositories.master.TewoRepository;
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
    private final TestaRepository testaRepository;
    private final TewoRepository tewoRepository;

    @Transactional
    @Override
    public List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();

        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(1L);

        for (TemplateServiceTask templateServiceTask : templateServiceTasks) {
            seot.add(new ServiceTaskReqDto(templateServiceTask.getTestaName(),
                    serviceOrders.getServices().getServStartDate(),
                    serviceOrders.getServices().getServStartDate().plusDays(1),
                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(),
                    serviceOrders, null));
        }

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);
        List<ServiceOrderTasks> serviceOrderTasks = soTasksRepository.saveAll(mapperTaskList);

        ServOrderWorkorderImpl servOrderWorkorder = new ServOrderWorkorderImpl(soWorkorderRepository, tewoRepository);
        servOrderWorkorder.addSowoList(serviceOrderTasks);

        log.info("ServOrderTaskImpl::addFeasiblityList successfully saved {} ",serviceOrderTasks);
        return serviceOrderTasks;
    }

    @Override
    public List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seotList = new ArrayList<>();
        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(2L);

        //String polisNumber = getPolisNumber();

        for (TemplateServiceTask templateServiceTask : templateServiceTasks) {
            seotList.add(new ServiceTaskReqDto(templateServiceTask.getTestaName(),
                    serviceOrders.getServices().getServStartDate(),
                    serviceOrders.getServices().getServStartDate().plusDays(1),
                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(), serviceOrders, null));
        }

        log.info("ServOrderTaskImpl::addPolisList the result of number polis is {} ", seotList.get(0).getGeneratePolisTasks());

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seotList, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(mapperTaskList);
    }

    private static String getPolisNumber() {
        Method generatePolis;
        try {
            generatePolis = SoAdapter.class.getMethod("generatePolisNumber", CustomerRequest.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        SoAdapter soAdapter = new SoAdapter();
        CustomerRequest cr = new CustomerRequest();
        String polisNumber;
        try {
            polisNumber = (String) generatePolis.invoke(soAdapter, cr);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return polisNumber;
    }

    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();
        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(3L);

        for (TemplateServiceTask templateServiceTask : templateServiceTasks) {
            seot.add(new ServiceTaskReqDto(templateServiceTask.getTestaName(),
                    serviceOrders.getServices().getServStartDate(),
                    serviceOrders.getServices().getServStartDate().plusDays(1),
                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getAreaWorkGroup(),
                    serviceOrders, null));
        }

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
