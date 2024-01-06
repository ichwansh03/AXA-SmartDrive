package com.app.smartdrive.api.controllers.service_order.servorder;

import com.app.smartdrive.api.dto.service_order.request.PagingServiceOrder;
import com.app.smartdrive.api.dto.service_order.request.ServiceOrderReqDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
import com.app.smartdrive.api.services.service_order.servorder.ServiceOrderFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@Slf4j
public class ServOrderController {

    private final ServOrderService servOrderService;
    private final ServiceOrderFactory serviceOrderFactory;

    @GetMapping("/search")
    @CrossOrigin
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> getAllBySeroId(@RequestParam("seroId") String seroId) {
        ServiceOrderRespDto orderDtoById = servOrderService.findOrderDtoById(seroId);
        return new ResponseEntity<>(orderDtoById, HttpStatus.OK);
    }

    @PutMapping("/partner/{seroId}")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateToAddPartner(@Valid @RequestBody Partner partner, @PathVariable("seroId") String seroId){
        serviceOrderFactory.selectPartner(partner, seroId);
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }

    @PutMapping("/close/{seroId}")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> updateToCloseOrder(@Valid @RequestBody ServiceOrderReqDto serviceOrderReqDto, @PathVariable("seroId") String seroId){
        int requested = serviceOrderFactory.updateStatusRequest(serviceOrderReqDto.getSeroStatus(), serviceOrderReqDto.getSeroReason(), seroId);
        return new ResponseEntity<>(requested, HttpStatus.OK);
    }

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('Employee') || hasAuthority('Admin')")
    public ResponseEntity<?> getPageServiceOrders(
            @Valid @RequestBody PagingServiceOrder pagingServiceOrder
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
