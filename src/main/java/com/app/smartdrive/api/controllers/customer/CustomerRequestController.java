package com.app.smartdrive.api.controllers.customer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.repositories.master.IntyRepository;
import com.app.smartdrive.api.repositories.master.TemiRepository;
import com.app.smartdrive.api.services.customer.CustomerRequestServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerRequestController {
    private final CustomerRequestServiceImpl customerRequestService;


    @GetMapping
    public List<CustomerRequest> getAll(){
        return this.customerRequestService.get();
    }

    @PostMapping
    public CustomerResponseDTO create(
        @Valid @RequestParam("client") String client,
         @RequestParam("file") MultipartFile[] files
        ) throws Exception{
        
        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);

        CustomerRequest customerRequest = this.customerRequestService.create(customerRequestDTO, files);
        
        CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
        .businessEntity(customerRequest.getBusinessEntity())
        .creqCreateDate(customerRequest.getCreqCreateDate())
        .creqStatus(customerRequest.getCreqStatus())
        .creqType(customerRequest.getCreqType())
        .customer(customerRequest.getCustomer())
        .customerInscAssets(customerRequest.getCustomerInscAssets())
        .build();

        return responseDTO;
    }



}
