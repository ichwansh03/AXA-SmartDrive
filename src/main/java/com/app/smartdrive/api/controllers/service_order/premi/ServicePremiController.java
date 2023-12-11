package com.app.smartdrive.api.controllers.service_order.premi;

import com.app.smartdrive.api.dto.service_order.request.SemiReqDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.premi.ServPremiService;
import com.app.smartdrive.api.services.service_order.servorder.ServService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/premi")
@RequiredArgsConstructor
public class ServicePremiController {

    private final ServPremiService servPremiService;
    private final ServService servService;

    @PostMapping
    public ResponseEntity<?> addServicePremi(@Valid @RequestBody SemiReqDto semiReqDto){
        ServicePremi servicePremi = ServicePremi.builder()
                .semiPaidType(semiReqDto.getSemiPaidType())
                .semiStatus(semiReqDto.getSemiStatus())
                .semiPremiDebet(semiReqDto.getSemiPremiDebet()).build();
        ServicePremi semi = servPremiService.addSemi(servicePremi, 1L);
        SemiReqDto semiReqDtos = TransactionMapper.mapEntityToDto(semi, SemiReqDto.class);
        return new ResponseEntity<>(semiReqDtos, HttpStatus.OK);
    }
}
