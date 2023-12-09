package com.app.smartdrive.api.controllers.customer;

import java.util.List;
import java.util.Objects;

import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerRequestController {
    private final CustomerRequestService customerRequestService;

    private final CustomerClaimService customerClaimService;

    @GetMapping
    public List<CustomerResponseDTO> getAll() {
        List<CustomerRequest> customerRequestList = this.customerRequestService.get();
        List<CustomerResponseDTO> customerRequestDTOList = customerRequestList.stream()
                .map(creq -> this.customerRequestService.convert(creq)).
                toList();
        return customerRequestDTOList;
    }

    @GetMapping("/page")
    public Page<CustomerResponseDTO> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "sortBy", defaultValue = "creqEntityId") String sortBy,
            @RequestParam(value = "sort", defaultValue = "ascending") String sort
    ) {
        Pageable paging;

        if(Objects.equals(sort, "descending")){
             paging = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }else {
             paging = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<CustomerResponseDTO> pagingCustomerResponseDTO = this.customerRequestService.getPaging(paging);


        return pagingCustomerResponseDTO;

    }

    @GetMapping("/request")
    public Page<CustomerResponseDTO> getAllCustomersRequest(
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

    @GetMapping("/search")
    public CustomerResponseDTO getById(
            @RequestParam("creqEntityId") Long creqEntityId
    ){
        CustomerResponseDTO customerResponseDTO = this.customerRequestService.getCustomerRequestById(creqEntityId);
        return customerResponseDTO;
    }


    @PostMapping
    public CustomerResponseDTO create(
            @Valid @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);

        CustomerResponseDTO customerRequest = this.customerRequestService.create(customerRequestDTO, files);

        return customerRequest;
    }

    @PutMapping
    public CustomerResponseDTO update(
            @RequestParam("creqEntityId") Long creqEntityId,
            @Valid @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateCustomerRequestDTO updateCustomerRequestDTO = mapper.readValue(client, UpdateCustomerRequestDTO.class);

        CustomerResponseDTO customerResponseDTO = this.customerRequestService.updateCustomerRequest(creqEntityId, updateCustomerRequestDTO, files);

        return customerResponseDTO;
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam("creqEntityId") Long creqEntityId
    ){
        this.customerRequestService.delete(creqEntityId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/claim")
    public ResponseEntity<CustomerResponseDTO> updateCustomerClaim(
            @RequestBody ClaimRequestDTO claimRequestDTO
    ){
        CustomerResponseDTO customerResponseDTO = this.customerClaimService.updateCustomerClaim(claimRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @GetMapping("/claim")
    public ResponseEntity<ClaimResponseDTO> getCustomerClaimById(
            @RequestParam("cuclCreqEntityId") Long cuclCreqEntityId
    ){
        ClaimResponseDTO existCustomerClaim = this.customerClaimService.getCustomerClaimById(cuclCreqEntityId);
        return ResponseEntity.status(HttpStatus.OK).body(existCustomerClaim);

    }

    @DeleteMapping("/claim")
    public ResponseEntity<Void> deleteCustomerClaim(
            @RequestParam("cuclCreqEntityId") Long cuclCreqEntityId
    ){
        this.customerClaimService.deleteCustomerClaim(cuclCreqEntityId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/close")
    public ResponseEntity<CustomerResponseDTO> requestClosePolis(@RequestBody CloseRequestDTO closeRequestDTO){
        CustomerResponseDTO customerResponseDTO = this.customerRequestService.closePolis(closeRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @PutMapping("/polis/type")
    public ResponseEntity<Void> changeTypePolis(@RequestBody CustomerRequestTypeDTO customerRequestTypeDTO){
        this.customerRequestService.changeRequestTypeToPolis(customerRequestTypeDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/claim/type")
    public ResponseEntity<Void> changeTypeClaim(@RequestBody CustomerRequestTypeDTO customerRequestTypeDTO){
        this.customerRequestService.changeRequestTypeToClaim(customerRequestTypeDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/close/type")
    public ResponseEntity<Void> changeTypeClose(@RequestBody CustomerRequestTypeDTO customerRequestTypeDTO){
        this.customerRequestService.changeRequestTypeToClose(customerRequestTypeDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}