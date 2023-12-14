package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderTaskService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sero")
@RequiredArgsConstructor
@Slf4j
public class ServOrderController {

    private final ServOrderService servOrderService;
    private final ServService servService;
    private final ServOrderTaskService servOrderTaskService;

    @GetMapping("/search")
    public ResponseEntity<?> getAllBySeroId(@RequestParam("seroId") String seroId) {
        ServiceOrders serviceOrders = servOrderService.findServiceOrdersById(seroId);

        ServiceOrderRespDto serviceOrderRespDto = responseServiceOrders(serviceOrders);

        return new ResponseEntity<>(serviceOrderRespDto, HttpStatus.OK);
    }

    @PutMapping("/partner/{seroId}")
    public ResponseEntity<?> updateToAddPartner(@Valid @RequestBody Partner partner, @PathVariable("seroId") String seroId){
        servOrderService.selectPartner(partner, seroId);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    private ServiceOrderRespDto responseServiceOrders(ServiceOrders serviceOrders) {
        Services servicesById = servService.findServicesById(serviceOrders.getServices().getServId());
        ServiceRespDto serviceRespDto = TransactionMapper.mapEntityToDto(servicesById, ServiceRespDto.class);

        List<ServiceOrderTasks> serviceOrderTasks = servOrderTaskService.findSeotBySeroId(serviceOrders.getSeroId());
        List<SoTasksDto> soTasksDtos = TransactionMapper.mapListDtoToListEntity(serviceOrderTasks, SoTasksDto.class);

        ServiceOrderRespDto serviceOrderRespDto = TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class);
        serviceOrderRespDto.setServices(serviceRespDto);
        serviceOrderRespDto.setSoTasksDtoList(soTasksDtos);

        return serviceOrderRespDto;
    }
}
