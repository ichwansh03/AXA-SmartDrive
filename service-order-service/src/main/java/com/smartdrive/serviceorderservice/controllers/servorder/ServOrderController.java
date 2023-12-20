package com.smartdrive.serviceorderservice.controllers.servorder;

import com.smartdrive.serviceorderservice.dto.request.PagingServiceOrder;
import com.smartdrive.serviceorderservice.dto.request.ServiceOrderReqDto;
import com.smartdrive.serviceorderservice.dto.response.ServiceOrderRespDto;
import com.smartdrive.serviceorderservice.entities.ServiceOrders;
import com.smartdrive.serviceorderservice.mapper.TransactionMapper;
import com.smartdrive.serviceorderservice.services.servorder.ServOrderService;
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

//    @PutMapping("/partner/{seroId}")
//    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
//    public ResponseEntity<?> updateToAddPartner(@Valid @RequestBody Partner partner, @PathVariable("seroId") String seroId){
//        servOrderService.selectPartner(partner, seroId);
//        return new ResponseEntity<>(partner, HttpStatus.OK);
//    }

    @PutMapping("/close/{seroId}")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateToCloseOrder(@Valid @RequestBody ServiceOrderReqDto serviceOrderReqDto, @PathVariable("seroId") String seroId){
        int requested = servOrderService.requestClosePolis(serviceOrderReqDto.getSeroStatus(), serviceOrderReqDto.getSeroReason(), seroId);
        return new ResponseEntity<>(requested, HttpStatus.OK);
    }

    @GetMapping("/request")
    //@PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> getPageServiceOrders(
            @Valid @RequestBody PagingServiceOrder pagingServiceOrder,
            @RequestParam("userId") Long userId
    ){

        Pageable paging;

        if(Objects.equals(pagingServiceOrder.getSort(), "descending")){
            paging = PageRequest.of(pagingServiceOrder.getPage(), pagingServiceOrder.getSize(), Sort.by(pagingServiceOrder.getSortBy()).descending());
        }else {
            paging = PageRequest.of(pagingServiceOrder.getPage(), pagingServiceOrder.getSize(), Sort.by(pagingServiceOrder.getSortBy()).ascending());
        }

        Page<ServiceOrderRespDto> orderRespDtoPage = servOrderService.pageServiceOrderByUserId(paging, pagingServiceOrder.getType(), pagingServiceOrder.getStatus());

        return new ResponseEntity<>(orderRespDtoPage, HttpStatus.OK);
    }
}
