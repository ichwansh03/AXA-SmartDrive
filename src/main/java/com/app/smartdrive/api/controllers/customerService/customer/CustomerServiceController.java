package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.servorder.orders.ServOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/service")
public class CustomerServiceController {
    private final CustomerRequestService customerRequestService;

    private final CustomerClaimService customerClaimService;

    private final ServOrderService servOrderService;

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<Page<CustomerResponseDTO>> getAllCustomersRequest(
            @Valid @RequestBody BasePagingCustomerRequestDTO basePagingCustomerRequestDTO
    ){
        Pageable paging;

        if(Objects.equals(basePagingCustomerRequestDTO.getSort(), "descending")){
            paging = PageRequest.of(basePagingCustomerRequestDTO.getPage(), basePagingCustomerRequestDTO.getSize(), Sort.by(basePagingCustomerRequestDTO.getSortBy()).descending());
        }else {
            paging = PageRequest.of(basePagingCustomerRequestDTO.getPage(), basePagingCustomerRequestDTO.getSize(), Sort.by(basePagingCustomerRequestDTO.getSortBy()).ascending());
        }

        Page<CustomerResponseDTO> pageCustomerResponseDTO = this.customerRequestService.getAllPaging(paging, basePagingCustomerRequestDTO.getType(), basePagingCustomerRequestDTO.getStatus());

        return ResponseEntity.status(HttpStatus.OK).body(pageCustomerResponseDTO);
    }

    @GetMapping("/request/customer")
    @PreAuthorize("hasAuthority('Customer')")
    public ResponseEntity<Page<CustomerResponseDTO>> getAllUserCustomersRequest(
            @Valid @RequestBody PagingUserCustomerRequestDTO pagingUserCustomerRequestDTO
    ){
        Pageable paging;

        if(Objects.equals(pagingUserCustomerRequestDTO.getSort(), "descending")){
            paging = PageRequest.of(pagingUserCustomerRequestDTO.getPage(), pagingUserCustomerRequestDTO.getSize(), Sort.by(pagingUserCustomerRequestDTO.getSortBy()).descending());
        }else {
            paging = PageRequest.of(pagingUserCustomerRequestDTO.getPage(), pagingUserCustomerRequestDTO.getSize(), Sort.by(pagingUserCustomerRequestDTO.getSortBy()).ascending());
        }

        Page<CustomerResponseDTO> pageCustomerResponseDTO = this.customerRequestService.getPagingUserCustomerRequest(pagingUserCustomerRequestDTO.getCustomerId(), paging, pagingUserCustomerRequestDTO.getType(), pagingUserCustomerRequestDTO.getStatus());



        return ResponseEntity.status(HttpStatus.OK).body(pageCustomerResponseDTO);
    }

    @GetMapping("/request/agen")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<Page<CustomerResponseDTO>> getAllAgenCustomersRequest(
            @Valid @RequestBody PagingAgenCustomerRequestDTO pagingAgenCustomerRequestDTO

    ){
        Pageable paging;

        if(Objects.equals(pagingAgenCustomerRequestDTO.getSort(), "descending")){
            paging = PageRequest.of(pagingAgenCustomerRequestDTO.getPage(), pagingAgenCustomerRequestDTO.getSize(), Sort.by(pagingAgenCustomerRequestDTO.getSortBy()).descending());
        }else {
            paging = PageRequest.of(pagingAgenCustomerRequestDTO.getPage(), pagingAgenCustomerRequestDTO.getSize(), Sort.by(pagingAgenCustomerRequestDTO.getSortBy()).ascending());
        }

        Page<CustomerResponseDTO> pageCustomerResponseDTO = this.customerRequestService.getPagingAgenCustomerRequest(pagingAgenCustomerRequestDTO.getEmployeeId(), pagingAgenCustomerRequestDTO.getArwgCode(), paging, pagingAgenCustomerRequestDTO.getType(), pagingAgenCustomerRequestDTO.getStatus());



        return ResponseEntity.status(HttpStatus.OK).body(pageCustomerResponseDTO);
    }


    @GetMapping("request/search")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> getById(
            @RequestParam("creqEntityId") Long creqEntityId
    ){
        CustomerRequest existCustomerRequest = this.customerRequestService.getById(creqEntityId);

        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(existCustomerRequest, CustomerResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);

    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee') or hasAuthority('Potential Customer')")
    public ResponseEntity<CustomerResponseDTO> create(
            @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);

        CustomerResponseDTO createdCustomerResponseDTO = this.customerRequestService.create(customerRequestDTO, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerResponseDTO);
    }

    @PostMapping("/request/agen")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> createByAgen(
            @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        CreateCustomerRequestByAgenDTO createCustomerRequestByAgenDTO = mapper.readValue(client, CreateCustomerRequestByAgenDTO.class);

        CustomerResponseDTO createdByAgenCustomerResponseDTO = this.customerRequestService.createByAgen(createCustomerRequestByAgenDTO, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdByAgenCustomerResponseDTO);
    }

    @PutMapping("/request")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> update(
            @Valid @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateCustomerRequestDTO updateCustomerRequestDTO = mapper.readValue(client, UpdateCustomerRequestDTO.class);

        CustomerResponseDTO updatedCustomerResponseDTO = this.customerRequestService.updateCustomerRequest(updateCustomerRequestDTO, files);

        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomerResponseDTO);
    }


    @PutMapping("/request/claim")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> requestClaimPolis(
            @RequestBody ClaimRequestDTO claimRequestDTO
    ){
        CustomerResponseDTO claimedCustomerResponseDTO = this.customerClaimService.claimPolis(claimRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(claimedCustomerResponseDTO);
    }

    @GetMapping("/request/claim")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<ClaimResponseDTO> getCustomerClaimById(
            @RequestParam("cuclCreqEntityId") Long cuclCreqEntityId
    ){
        ClaimResponseDTO claimResponseDTO = this.customerClaimService.getCustomerClaimById(cuclCreqEntityId);

        return ResponseEntity.status(HttpStatus.OK).body(claimResponseDTO);

    }

    @PutMapping("/request/close")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> requestClosePolis(@RequestBody CloseRequestDTO closeRequestDTO){
        CustomerResponseDTO closedCustomerResponseDTO = this.customerClaimService.closePolis(closeRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(closedCustomerResponseDTO);
    }




    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequestClosePolis(@RequestParam("userentityid") Long custId){
        List<ServiceOrders> bySeroStatus = servOrderService.findAllSeroByUserId(custId);
        List<ServiceOrderRespDto> serviceOrderRespDtos = TransactionMapper.mapEntityListToDtoList(bySeroStatus, ServiceOrderRespDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(serviceOrderRespDtos);
    }


}
