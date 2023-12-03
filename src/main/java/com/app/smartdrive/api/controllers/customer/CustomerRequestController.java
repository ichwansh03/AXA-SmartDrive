package com.app.smartdrive.api.controllers.customer;

import java.util.List;
import java.util.Objects;

import com.app.smartdrive.api.dto.customer.request.UpdateCustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerRequestController {
    private final CustomerRequestService customerRequestService;

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

        Page<CustomerResponseDTO> pagingCustomerResponseDTO = this.customerRequestService.getPagingCustomer(custId, paging, type, status);

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



}