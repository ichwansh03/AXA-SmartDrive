package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.service_order.response.ServiceOrderRespDto;
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
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "type", defaultValue = "ALL") String type,
            @RequestParam(value = "status", defaultValue = "OPEN") String status,
            @RequestParam(value = "sortBy", defaultValue = "creqEntityId") String sortBy,
            @RequestParam(value = "sort", defaultValue = "ascending") String sort

    ){
        Pageable paging;

        if(Objects.equals(sort, "descending")){
            paging = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }else {
            paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<CustomerRequest> pagingCustomerRequest = this.customerRequestService.getAllPaging(paging, type, status);

        Page<CustomerResponseDTO> pagingCustomerResponseDTO = pagingCustomerRequest.map(new Function<CustomerRequest, CustomerResponseDTO>() {
            @Override
            public CustomerResponseDTO apply(CustomerRequest customerRequest) {
                return TransactionMapper.mapEntityToDto(customerRequest, CustomerResponseDTO.class);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(pagingCustomerResponseDTO);
    }

    @GetMapping("/request/customer")
    @PreAuthorize("hasAuthority('Customer')")
    public ResponseEntity<Page<CustomerResponseDTO>> getAllUserCustomersRequest(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "type", defaultValue = "ALL") String type,
            @RequestParam(value = "status", defaultValue = "OPEN") String status,
            @RequestParam(value = "sort", defaultValue = "ascending") String sort,
            @RequestParam(value = "customerId") Long customerId
    ){
        Pageable paging;

        if(Objects.equals(sort, "descending")){
            paging = PageRequest.of(page, size, Sort.by("creqEntityId").descending());
        }else {
            paging = PageRequest.of(page, size, Sort.by("creqEntityId").ascending());
        }

        Page<CustomerRequest> pagingUserCustomerRequest = this.customerRequestService.getPagingUserCustomerRequest(customerId, paging, type, status);

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
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "type", defaultValue = "ALL") String type,
            @RequestParam(value = "status", defaultValue = "OPEN") String status,
            @RequestParam(value = "sort", defaultValue = "ascending") String sort,
            @RequestParam(value = "employeeId") Long employeeId,
            @RequestParam(value = "arwgCode") String arwgCode

    ){
        Pageable paging;

        if(Objects.equals(sort, "descending")){
            paging = PageRequest.of(page, size, Sort.by("creqEntityId").descending());
        }else {
            paging = PageRequest.of(page, size, Sort.by("creqEntityId").ascending());
        }

        Page<CustomerRequest> pagingAgenCustomerRequest = this.customerRequestService.getPagingAgenCustomerRequest(employeeId, arwgCode, paging, type, status);

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
    public ResponseEntity<CustomerResponseDTO> updateCustomerClaim(
            @RequestBody ClaimRequestDTO claimRequestDTO
    ){
        CustomerResponseDTO customerResponseDTO = this.customerClaimService.claimPolis(claimRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @GetMapping("/request/claim")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<ClaimResponseDTO> getCustomerClaimById(
            @RequestParam("cuclCreqEntityId") Long cuclCreqEntityId
    ){
        ClaimResponseDTO existCustomerClaim = this.customerClaimService.getCustomerClaimById(cuclCreqEntityId);
        return ResponseEntity.status(HttpStatus.OK).body(existCustomerClaim);

    }

    @PutMapping("/request/close")
    @PreAuthorize("hasAuthority('Customer') or hasAuthority('Employee')")
    public ResponseEntity<CustomerResponseDTO> requestClosePolis(@RequestBody CloseRequestDTO closeRequestDTO){
        CustomerResponseDTO customerResponseDTO = this.customerClaimService.closePolis(closeRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }




    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequestClosePolis(@RequestParam("userentityid") Long custId){
        List<ServiceOrders> bySeroStatus = servOrderService.findAllSeroByUserId(custId);
        List<ServiceOrderRespDto> serviceOrderRespDtos = TransactionMapper.mapEntityListToDtoList(bySeroStatus, ServiceOrderRespDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(serviceOrderRespDtos);
    }


}
