package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.service_order.dto.SoDto;
import com.app.smartdrive.api.entities.service_order.dto.SoTasksDto;
import com.app.smartdrive.api.entities.service_order.dto.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServicesController {

    private final SoService soService;
    private final SoOrderService soOrderService;
    private final SoTasksService soTasksService;

    @GetMapping("/search")
    public ResponseEntity<?> getSearchById(@RequestParam("seroId") String seroId){

        Services services = soService.getById(3L);
        ServiceOrders serviceOrders = soOrderService.getById(seroId);
        List<ServiceOrderTasks> seot = soTasksService.findAllBySeotSeroId(seroId);

        List<SoTasksDto> soTasksDtos = seot.stream()
                .map(task -> {
                    List<SoWorkorderDto> workorderDtos = task.getServiceOrderWorkordersSet().stream()
                            .map(workorder -> SoWorkorderDto.builder()
                                    .sowoName(workorder.getSowoName())
                                    .sowoStatus(workorder.getSowoStatus())
                                    .build()).toList();

                    return SoTasksDto.builder()
                            .taskId(task.getSeotId())
                            .taskName(task.getSeotName())
                            .startDate(task.getSeotStartDate())
                            .endDate(task.getSeotEndDate())
                            .status(task.getSeotStatus())
                            .serviceOrderWorkorder(workorderDtos).build();
                }).toList();

        SoDto soDto = SoDto.builder()
                .seroId(seroId)
                .servCreatedOn(services.getServCreatedOn())
                .servType(services.getServType())
                .servStatus(services.getServStatus())
                .noPolis(services.getServInsuranceNo())
                .customerName(services.getServCustEntityId())
                .empName(serviceOrders.getSeroAgentEntityid())
                .serviceOrderTasksList(soTasksDtos).build();

        return new ResponseEntity<>(soDto, HttpStatus.OK);
    }

    @GetMapping("/serv")
    public ResponseEntity<?> generateServices(@RequestParam("servId") Long servId){

        return new ResponseEntity<>(soService.getById(servId), HttpStatus.OK);
    }

    @GetMapping("/servorder")
    public ResponseEntity<?> getServiceOrderById(@RequestParam("seroid") String seroId){
        return new ResponseEntity<>(soOrderService.getById(seroId), HttpStatus.OK);
    }

    private String formatServiceOrderId(String servType, Long seroId, LocalDate createdAt){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatSeroId = String.format("%04d", seroId);

        if (servType.equals("POLIS")){
            return "PL"+formatSeroId+"-"+createdAt.format(formatter);
        } else if (servType.equals("CLAIM")) {
            return "CL"+formatSeroId+"-"+createdAt.format(formatter);
        }
        return "TP"+formatSeroId+"-"+createdAt.format(formatter);
    }
}
