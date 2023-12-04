package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.dto.service_order.response.ServSeroDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ArwgService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sero")
@RequiredArgsConstructor
@Slf4j
public class ServOrderController {

    private final ServOrderService servOrderService;
    private final ServService servService;
    private final ServOrderTaskService servOrderTaskService;
    private final ArwgService arwgService;

    @GetMapping
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){

        ServiceOrders seroById = servOrderService.findServiceOrdersById(seroId);

        Services servicesById = servService.findServicesById(seroById.getServices().getServId()).get();
        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(servicesById, ServiceRespDto.class);

        AreaWorkGroup arwgByCode = arwgService.getById(seroById.getAreaWorkGroup().getArwgCode());
        ArwgRes arwgRes = TransactionMapper.mapEntityToDto(arwgByCode, ArwgRes.class);

        List<ServiceOrderTasks> serviceOrderTasks = servOrderTaskService.findSeotBySeroId(seroId);
        List<SoTasksDto> soTasksDtos = TransactionMapper.mapListDtoToListEntity(serviceOrderTasks, SoTasksDto.class);

        ServiceOrderRespDto serviceOrderRespDto = TransactionMapper.mapEntityToDto(seroById, ServiceOrderRespDto.class);
        serviceOrderRespDto.setServices(serviceRespDto);
        serviceOrderRespDto.setArwgRes(arwgRes);
        serviceOrderRespDto.setSoTasksDtoList(soTasksDtos);

        log.info("ServiceOrdersController::getServiceOrderById successfully viewed");
        return new ResponseEntity<>(serviceOrderRespDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllBySeroId(@RequestParam("seroId") String seroId){
        ServiceOrders serviceOrders = servOrderService.findServiceOrdersById(seroId);

        ServSeroDto servSeroDto = ServSeroDto.builder()
                .seroId(seroId)
                .servCreatedOn(serviceOrders.getServices().getServCreatedOn())
                .seroStatus(serviceOrders.getSeroStatus())
                .userName(serviceOrders.getServices().getUsers().getUserName())
                .empName(serviceOrders.getEmployees().getEmpName()).build();

        return new ResponseEntity<>(servSeroDto, HttpStatus.OK);
    }
}
