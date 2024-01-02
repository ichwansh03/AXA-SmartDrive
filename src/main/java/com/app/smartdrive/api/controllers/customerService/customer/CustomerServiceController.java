package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.service_order.servorder.ServOrderService;
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
import java.util.function.Function;

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

        Page<CustomerRequest> pagingUserCustomerRequest = this.customerRequestService.getPagingUserCustomerRequest(pagingUserCustomerRequestDTO.getCustomerId(), paging, pagingUserCustomerRequestDTO.getType(), pagingUserCustomerRequestDTO.getStatus());

        Page<CustomerResponseDTO> pagingUserCustomerResponseDTO = pagingUserCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(pagingUserCustomerResponseDTO);
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

        Page<CustomerRequest> pagingAgenCustomerRequest = this.customerRequestService.getPagingAgenCustomerRequest(pagingAgenCustomerRequestDTO.getEmployeeId(), pagingAgenCustomerRequestDTO.getArwgCode(), paging, pagingAgenCustomerRequestDTO.getType(), pagingAgenCustomerRequestDTO.getStatus());

        Page<CustomerResponseDTO> pagingAgenCustomerResponseDTO = pagingAgenCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(pagingAgenCustomerResponseDTO);
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

        CustomerRequest createdCustomerRequest = this.customerRequestService.create(customerRequestDTO, files);

        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(createdCustomerRequest, CustomerResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @PostMapping("/request/agen")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> createByAgen(
            @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        CreateCustomerRequestByAgenDTO createCustomerRequestByAgenDTO = mapper.readValue(client, CreateCustomerRequestByAgenDTO.class);

        CustomerRequest newCustomerRequest = this.customerRequestService.createByAgen(createCustomerRequestByAgenDTO, files);

        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(newCustomerRequest, CustomerResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @PutMapping("/request")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> update(
            @Valid @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateCustomerRequestDTO updateCustomerRequestDTO = mapper.readValue(client, UpdateCustomerRequestDTO.class);

        CustomerRequest updatedCustomerRequest = this.customerRequestService.updateCustomerRequest(updateCustomerRequestDTO, files);

        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(updatedCustomerRequest, CustomerResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }


    @PutMapping("/request/claim")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> requestClaimPolis(
            @RequestBody ClaimRequestDTO claimRequestDTO
    ){
        CustomerRequest claimedCustomerRequest = this.customerClaimService.claimPolis(claimRequestDTO);

        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(claimedCustomerRequest, CustomerResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @GetMapping("/request/claim")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<ClaimResponseDTO> getCustomerClaimById(
            @RequestParam("cuclCreqEntityId") Long cuclCreqEntityId
    ){
        CustomerClaim existCustomerClaim = this.customerClaimService.getCustomerClaimById(cuclCreqEntityId);

        ClaimResponseDTO claimResponseDTO = TransactionMapper.mapEntityToDto(existCustomerClaim, ClaimResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(claimResponseDTO);

    }

    @PutMapping("/request/close")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> requestClosePolis(@RequestBody CloseRequestDTO closeRequestDTO){
        CustomerRequest closedCustomerRequest = this.customerClaimService.closePolis(closeRequestDTO);

        CustomerResponseDTO customerResponseDTO = TransactionMapper.mapEntityToDto(closedCustomerRequest, CustomerResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }




    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequestClosePolis(@RequestParam("userentityid") Long custId){
        List<ServiceOrders> bySeroStatus = servOrderService.findAllSeroByUserId(custId);
        List<ServiceOrderRespDto> serviceOrderRespDtos = TransactionMapper.mapEntityListToDtoList(bySeroStatus, ServiceOrderRespDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(serviceOrderRespDtos);
    }


}
