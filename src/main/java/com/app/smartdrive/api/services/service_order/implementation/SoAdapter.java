package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ServiceOrderTasks;
import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.dto.service_order.response.SoWorkorderDto;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.services.service_order.ServOrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    private ServOrderService servOrderService;

//    public ServSeroDto generateServiceDto(String seroId){
//        Stream<ServiceOrderTasks> seot = servOrderService.findAllBySeotSeroId(seroId);
//
//        List<SoTasksDto> soTasksDtos = seot
//                .map(this::generateSoTasksDto)
//                .collect(Collectors.toList());
//
//        ServSeroDto servSeroDto = servOrderService.findDtoById(seroId);
//        servSeroDto.setServiceOrderTasksList(soTasksDtos);
//
//        log.info("ServicesDto::generateServiceDto created successfully {}", servSeroDto);
//
//        return servSeroDto;
//    }

    public SoTasksDto generateSoTasksDto(ServiceOrderTasks seot){
        List<SoWorkorderDto> workorderDtos = seot.getServiceOrderWorkorders().stream()
                .map(this::generateSoWorkorderDto)
                .collect(Collectors.toList());

        return SoTasksDto.builder()
                .seotId(seot.getSeotId())
                .seotName(seot.getSeotName())
                .seotStartDate(seot.getSeotStartDate())
                .seotEndDate(seot.getSeotEndDate())
                .seotStatus(seot.getSeotStatus())
                .serviceOrderWorkorders(workorderDtos).build();
    }

    public SoWorkorderDto generateSoWorkorderDto(ServiceOrderWorkorder sowo) {
        return SoWorkorderDto.builder()
                .sowoName(sowo.getSowoName())
                .sowoStatus(sowo.getSowoStatus())
                .build();
    }

    public String formatServiceOrderId(Services services, Long userId, LocalDateTime endDate){

        String servTypes = services.getServType().toString();

        log.info("Format ID for ServiceOrders has been created");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatSeroId = String.format("%04d", userId);
        String formatEndDate = endDate.format(formatter);

        return switch (servTypes) {
            case "POLIS" -> "PL" + formatSeroId + "-" + formatEndDate;
            case "CLAIM" -> "CL" + formatSeroId + "-" + formatEndDate;
            case "FEASIBLITY" -> "FS" + formatSeroId + "-" + formatEndDate;
            default -> "TP" + formatSeroId + "-" + formatEndDate;
        };

    }

}
