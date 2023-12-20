package com.smartdrive.serviceorderservice.services.servorder.impl;

import com.smartdrive.serviceorderservice.dto.request.ServiceTaskReqDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrderTasks;
import com.smartdrive.serviceorderservice.entities.ServiceOrders;
import com.smartdrive.serviceorderservice.entities.enumerated.EnumModuleServiceOrders;
import com.smartdrive.serviceorderservice.mapper.TransactionMapper;
import com.smartdrive.serviceorderservice.repositories.SoTasksRepository;
import com.smartdrive.serviceorderservice.services.SoAdapter;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderTaskService;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderWorkorderService;
import com.smartdrive.serviceorderservice.services.servorder.ServiceTasksFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceTasksFactoryImpl implements ServiceTasksFactory {

    private final SoTasksRepository soTasksRepository;
    //private final TestaRepository testaRepository;

    private final ServOrderWorkorderService servOrderWorkorderService;
    private final ServOrderTaskService servOrderTaskService;

    @Transactional
    @Override
    public List<ServiceOrderTasks> addFeasiblityList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();

//        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(1L);
//
//        for (int i = 0; i < templateServiceTasks.size(); i++) {
//            seot.add(new ServiceTaskReqDto(templateServiceTasks.get(i).getTestaName(),
//                    serviceOrders.getServices().getServStartDate().plusDays(i),
//                    serviceOrders.getServices().getServStartDate().plusDays(i+1),
//                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getEmployees().getAreaWorkGroup(),
//                    serviceOrders, null));
//        }

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);
        List<ServiceOrderTasks> serviceOrderTasks = soTasksRepository.saveAll(mapperTaskList);

        servOrderWorkorderService.addSowoList(serviceOrderTasks);

        return serviceOrderTasks;
    }

    @Override
    public List<ServiceOrderTasks> addPolisList(ServiceOrders serviceOrders) throws Exception {

        List<ServiceTaskReqDto> seotList = new ArrayList<>();
//        EmailReq emailReq = new EmailReq();
//        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(2L);
//
//        Method generatePolisNumber = SoAdapter.class.getMethod("generatePolis", CustomerRequest.class);
//
//        for (TemplateServiceTask templateServiceTask : templateServiceTasks) {
//            seotList.add(new ServiceTaskReqDto(templateServiceTask.getTestaName(),
//                    serviceOrders.getServices().getServStartDate(),
//                    serviceOrders.getServices().getServStartDate().plusDays(1),
//                    EnumModuleServiceOrders.SeotStatus.COMPLETED, serviceOrders.getEmployees().getAreaWorkGroup(),
//                    serviceOrders, generatePolisNumber));
//
//            switch (templateServiceTask.getTestaName()){
//                case "NOTIFY TO AGENT" -> servOrderTaskService.notifyTask(emailReq, serviceOrders,
//                        "Request new POLIS",
//                        "Request new POLIS from "+serviceOrders.getServices().getUsers().getUserFullName());
//                case "NOTIFY TO CUSTOMER" -> servOrderTaskService.notifyTask(emailReq, serviceOrders,
//                        "POLIS has been created",
//                        "Your request POLIS has been created, check your dashboard page");
//            }
//        }

//        log.info("ServOrderTaskImpl::addPolisList the result of number polis is {} ", generatePolisNumber);

        List<ServiceOrderTasks> mapperTaskList = TransactionMapper.mapListDtoToListEntity(seotList, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(mapperTaskList);
    }

    @Override
    public List<ServiceOrderTasks> addClaimList(ServiceOrders serviceOrders) {

        List<ServiceTaskReqDto> seot = new ArrayList<>();
//        EmailReq emailReq = new EmailReq();
//        List<TemplateServiceTask> templateServiceTasks = testaRepository.findByTestaTetyId(3L);
//
//        for (int i = 0; i < templateServiceTasks.size(); i++) {
//            seot.add(new ServiceTaskReqDto(templateServiceTasks.get(i).getTestaName(),
//                    serviceOrders.getServices().getServStartDate().plusDays(i),
//                    serviceOrders.getServices().getServStartDate().plusDays(i+1),
//                    EnumModuleServiceOrders.SeotStatus.INPROGRESS, serviceOrders.getEmployees().getAreaWorkGroup(),
//                    serviceOrders, null));
//
//            switch (templateServiceTasks.get(i).getTestaName()){
//                case "CLAIM DOCUMENT APPROVED" -> {
//                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
//                        servOrderTaskService.notifyTask(emailReq, serviceOrders,
//                                "Repair Sparepart from Customer",
//                                "Repair from "+serviceOrders.getServices().getUsers().getUserFullName());
//                    }
//                }
//
//                case "CALCULATE SPARE PART" -> {
//                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
//                        servOrderTaskService.notifyTask(emailReq, serviceOrders,
//                                "Your car is finish to repair",
//                                "Car Repaired is finish");
//                    }
//                }
//
//                case "NOTIFY CUSTOMER VEHICLE REPAIRED" -> {
//                    if (seot.get(i).getSeotStatus() == EnumModuleServiceOrders.SeotStatus.COMPLETED) {
//                        servOrderTaskService.notifyTask(emailReq, serviceOrders,
//                                "Claim for "+serviceOrders.getServices().getUsers().getUserFullName(),
//                                "Repaired is finish, pay claim to user");
//                    }
//                }
//            }
//        }

        List<ServiceOrderTasks> serviceOrderTasks = TransactionMapper.mapListDtoToListEntity(seot, ServiceOrderTasks.class);

        return soTasksRepository.saveAll(serviceOrderTasks);
    }

}
