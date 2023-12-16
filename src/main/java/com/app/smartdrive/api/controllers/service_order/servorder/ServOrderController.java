package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sero")
@RequiredArgsConstructor
@Slf4j
public class ServOrderController {

    private final ServOrderService servOrderService;

    @GetMapping("/search")
    public ResponseEntity<?> getAllBySeroId(@RequestParam("seroId") String seroId) {
        ServiceOrders serviceOrders = servOrderService.findServiceOrdersById(seroId);
        ServiceOrderRespDto serviceOrderRespDto = TransactionMapper.mapEntityToDto(serviceOrders, ServiceOrderRespDto.class);
        return new ResponseEntity<>(serviceOrderRespDto, HttpStatus.OK);
    }

    @PutMapping("/partner/{seroId}")
    public ResponseEntity<?> updateToAddPartner(@Valid @RequestBody Partner partner, @PathVariable("seroId") String seroId){
        servOrderService.selectPartner(partner, seroId);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }
}
