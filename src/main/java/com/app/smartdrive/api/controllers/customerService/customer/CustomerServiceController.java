package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.dto.customer.request.ClaimRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CloseRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.request.UpdateCustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/service")
public class CustomerServiceController {
    private final CustomerRequestService customerRequestService;

    @GetMapping("/request")
    public ResponseEntity<Page<CustomerResponseDTO>> getAllUserCustomersRequest(
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

        return ResponseEntity.status(HttpStatus.OK).body(pagingCustomerResponseDTO);
    }



    @GetMapping("request/search")
    public ResponseEntity<CustomerResponseDTO> getById(
            @RequestParam("creqEntityId") Long creqEntityId
    ){
        CustomerResponseDTO customerResponseDTO = this.customerRequestService.getCustomerRequestById(creqEntityId);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);

    }

    @PostMapping("/request")
    public ResponseEntity<CustomerResponseDTO> create(
            @Valid @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);

        CustomerResponseDTO customerResponseDTO = this.customerRequestService.create(customerRequestDTO, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @PutMapping("/request")
    public ResponseEntity<CustomerResponseDTO> update(
            @RequestParam("creqEntityId") Long creqEntityId,
            @Valid @RequestParam("client") String client,
            @RequestParam("file") MultipartFile[] files
    ) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UpdateCustomerRequestDTO updateCustomerRequestDTO = mapper.readValue(client, UpdateCustomerRequestDTO.class);

        CustomerResponseDTO customerResponseDTO = this.customerRequestService.updateCustomerRequest(creqEntityId, updateCustomerRequestDTO, files);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }


    @PutMapping("/request/claim")
    public ResponseEntity<CustomerResponseDTO> updateCustomerClaim(
            @RequestBody ClaimRequestDTO claimRequestDTO
    ){
        CustomerResponseDTO customerResponseDTO = this.customerRequestService.updateCustomerClaim(claimRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }

    @GetMapping("/request/claim")
    public ResponseEntity<ClaimResponseDTO> getCustomerClaimById(
            @RequestParam("cuclCreqEntityId") Long cuclCreqEntityId
    ){
        ClaimResponseDTO existCustomerClaim = this.customerRequestService.getCustomerClaimById(cuclCreqEntityId);
        return ResponseEntity.status(HttpStatus.OK).body(existCustomerClaim);

    }

    @PutMapping("/request/close")
    public ResponseEntity<CustomerResponseDTO> requestClosePolis(@RequestBody CloseRequestDTO closeRequestDTO){
        CustomerResponseDTO customerResponseDTO = this.customerRequestService.closePolis(closeRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTO);
    }



}
