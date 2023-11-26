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

import com.app.smartdrive.api.dto.customer.request.AgenDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.AgenResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CadocResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CiasResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CuexResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.master.TemplateInsurancePremiDto;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer.CadocCategory;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.entities.users.UserAddress;
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
    public List<CustomerResponseDTO> getAll(){
        List<CustomerRequest> creqList = this.customerRequestService.get();
        List<CustomerResponseDTO> creqResponseDTOList = creqList.stream().map(creq -> this.customerRequestService.convert(creq)).toList();

        return creqResponseDTOList;
    }

    @PostMapping
    public CustomerResponseDTO create(
        @Valid @RequestParam("client") String client,
         @RequestParam("file") MultipartFile[] files
        ) throws Exception{
        
        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);

        CustomerRequest customerRequest = this.customerRequestService.create(customerRequestDTO, files);
        

        return this.customerRequestService.convert(customerRequest);
        // return customerRequest;
    }

    // city yang buat error
    // gua apus dari agenDTO
    @PostMapping("/agen")
    public CustomerResponseDTO setAgen(@RequestBody AgenDTO agenDTO){
        CustomerRequest customerRequest = this.customerRequestService.setAgenCreq(agenDTO);
        Employees agen = customerRequest.getEmployee();

        AgenResponseDTO agenResponseDTO = AgenResponseDTO.builder()
        .agenId(agen.getEmpEntityid())
        .agenName(agen.getEmpName())
        .agenAccountNumber(agen.getEmpAccountNumber())
        .build();

        CustomerResponseDTO responseDTO = this.customerRequestService.convert(customerRequest);
        responseDTO.setAgen(agenResponseDTO);

        return responseDTO;
    }



}
