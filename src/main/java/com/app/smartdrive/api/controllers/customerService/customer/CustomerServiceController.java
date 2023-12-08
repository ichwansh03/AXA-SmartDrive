package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/service")
public class CustomerServiceController {
    private final CustomerRequestService customerRequestService;

    @GetMapping("/request")
    public Page<CustomerResponseDTO> getAllUserCustomersRequest(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "type", defaultValue = "ALL") String type,
            @RequestParam(value = "status", defaultValue = "OPEN") String status,
            @RequestParam(value = "sort", defaultValue = "ascending") String sort,
            @RequestParam(value = "custId") Long custId
    ){
        Pageable paging;

        if(Objects.equals(sort, "descending")){
            paging = PageRequest.of(page, size, Sort.by("creqEntityId").descending());
        }else {
            paging = PageRequest.of(page, size, Sort.by("creqEntityId").ascending());
        }

        Page<CustomerResponseDTO> pagingCustomerResponseDTO = this.customerRequestService.getPagingUserCustomerRequests(custId, paging, type, status);

        return pagingCustomerResponseDTO;
    }



    @GetMapping("request/search")
    public CustomerResponseDTO getById(
            @RequestParam("creqEntityId") Long creqEntityId
    ){
        CustomerResponseDTO customerResponseDTO = this.customerRequestService.getCustomerRequestById(creqEntityId);
        return customerResponseDTO;

    }






}
