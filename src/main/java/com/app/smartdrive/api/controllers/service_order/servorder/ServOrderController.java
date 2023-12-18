package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

    @GetMapping("/request")
    public ResponseEntity<Page<ServiceOrderRespDto>> getPageServiceOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "type", defaultValue = "ALL") String type,
            @RequestParam(value = "status", defaultValue = "OPEN") String status,
            @RequestParam(value = "sortBy", defaultValue = "seroId") String sortBy,
            @RequestParam(value = "sort", defaultValue = "ascending") String sort

    ){
        Pageable paging;

        if(Objects.equals(sort, "descending")){
            paging = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }else {
            paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<ServiceOrderRespDto> orderRespDtoPage = servOrderService.pageServiceOrderByUserId(paging, type, status);

        return ResponseEntity.status(HttpStatus.OK).body(orderRespDtoPage);
    }

}
