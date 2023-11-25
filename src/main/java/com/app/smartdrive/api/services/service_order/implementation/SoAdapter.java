package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.dto.service_order.ServicesDto;
import com.app.smartdrive.api.dto.service_order.SoTasksDto;
import com.app.smartdrive.api.dto.service_order.SoWorkorderDto;
import com.app.smartdrive.api.services.service_order.SoOrderService;
import com.app.smartdrive.api.services.service_order.SoService;
import com.app.smartdrive.api.services.service_order.SoTasksService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
@Slf4j
public class SoAdapter {

    private SoService soService;
    private SoOrderService soOrderService;
    private SoTasksService soTasksService;

    public ServicesDto generateServiceDto(Long seroId){
        Stream<ServiceOrderTasks> seot = soTasksService.findAllBySeotSeroId(seroId);

        List<SoTasksDto> soTasksDtos = seot
                .map(this::generateSoTasksDto)
                .collect(Collectors.toList());

        ServicesDto servicesDto = soOrderService.findDtoById(seroId);
        servicesDto.setServiceOrderTasksList(soTasksDtos);

        log.info("ServicesDto::generateServiceDto created successfully {}",servicesDto);

        return servicesDto;
    }

    public SoTasksDto generateSoTasksDto(ServiceOrderTasks seot){
        List<SoWorkorderDto> workorderDtos = seot.getServiceOrderWorkorders().stream()
                .map(this::generateSoWorkorderDto)
                .collect(Collectors.toList());

        return SoTasksDto.builder()
                .taskId(seot.getSeotId())
                .taskName(seot.getSeotName())
                .startDate(seot.getSeotStartDate())
                .endDate(seot.getSeotEndDate())
                .status(seot.getSeotStatus())
                .serviceOrderWorkorder(workorderDtos).build();
    }

    public SoWorkorderDto generateSoWorkorderDto(ServiceOrderWorkorder sowo) {
        return SoWorkorderDto.builder()
                .sowoName(sowo.getSowoName())
                .sowoStatus(sowo.getSowoStatus())
                .build();
    }

    public String formatServiceOrderId(EnumCustomer.CreqType servType, Long servId, LocalDateTime createdAt){

        log.info("Format ID for ServiceOrders has been created");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatSeroId = String.format("%04d", servId);

        if (servType.equals("POLIS")) return "PL"+formatSeroId+"-"+createdAt.format(formatter);
        else if (servType.equals("CLAIM")) return "CL"+formatSeroId+"-"+createdAt.format(formatter);

        return "TP"+formatSeroId+"-"+createdAt.format(formatter);
    }

}
