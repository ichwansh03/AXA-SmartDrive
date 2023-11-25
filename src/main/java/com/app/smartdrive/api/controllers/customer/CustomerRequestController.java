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
    public List<CustomerRequest> getAll(){
        return this.customerRequestService.get();
    }

    @PostMapping
    public CustomerRequest create(
        @Valid @RequestParam("client") String client,
         @RequestParam("file") MultipartFile[] files
        ) throws Exception{
        
        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);

        CustomerRequest customerRequest = this.customerRequestService.create(customerRequestDTO, files);
        
        // CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
        // .creqEntityId(customerRequest.getCreqEntityId())
        // .businessEntity(customerRequest.getBusinessEntity())
        // .creqCreateDate(customerRequest.getCreqCreateDate())
        // .creqStatus(customerRequest.getCreqStatus())
        // .creqType(customerRequest.getCreqType())
        // .customer(customerRequest.getCustomer())
        // .customerInscAssets(customerRequest.getCustomerInscAssets())
        // .build();

        // return responseDTO;

        return customerRequest;
    }

    @PostMapping("/agen")
    public CustomerResponseDTO setAgen(@RequestBody AgenDTO agenDTO){
        CustomerRequest customerRequest = this.customerRequestService.setAgenCreq(agenDTO);
        Employees agen = customerRequest.getEmployee();
        CustomerInscAssets cias = customerRequest.getCustomerInscAssets();
        List<CustomerInscDoc> cadoc = cias.getCustomerInscDoc();
        List<CustomerInscExtend> cuex = cias.getCustomerInscExtend();

        List<UserAddress> userAddress = agen.getUser().getUserAddress();
        List<Cities> cityList = userAddress.stream().map(address -> address.getCity()).toList();

        AgenResponseDTO agenResponseDTO = AgenResponseDTO.builder()
        .agenId(agen.getEmpEntityid())
        .agenName(agen.getEmpName())
        .agenAccountNumber(agen.getEmpAccountNumber())
        .agenCity(cityList)
        .build();

        List<CadocResponseDTO> cadocListDTO = cadoc.stream().map(doc -> new CadocResponseDTO(
            doc.getCadocCreqEntityid(), doc.getCadocFilename(),
            doc.getCadocFiletype(),
            doc.getCadocFilesize(),
            doc.getCadocCategory(),
            doc.getCadocModifiedDate())
        ).toList();

        List<CuexResponseDTO> cuexListDTO = cuex.stream().map(extend -> new CuexResponseDTO(
            extend.getCuexId(),
            extend.getCuexCreqEntityid(),
            extend.getCuexName(),
            extend.getCuexTotalItem(),
            extend.getCuex_nominal()
        )).toList();

        CiasResponseDTO ciasResponseDTO = CiasResponseDTO.builder()
        .ciasCreqEntityid(cias.getCiasCreqEntityid())
        .ciasCurrentPrice(cias.getCiasCurrentPrice())
        .ciasStartdate(cias.getCiasStartdate())
        .ciasEnddate(cias.getCiasEnddate())
        .ciasIsNewChar(cias.getCiasIsNewChar())
        .ciasPaidType(cias.getCiasPaidType())
        .ciasPoliceNumber(cias.getCiasPoliceNumber())
        .ciasTotalPremi(cias.getCiasTotalPremi())
        .ciasYear(cias.getCiasYear())
        .city(cias.getCity().getCityName())
        .cuexResponseDTOList(cuexListDTO)
        .cadocResponseDTOList(cadocListDTO)
        .build();


        CustomerResponseDTO responseDTO = CustomerResponseDTO.builder()
        .businessEntity(customerRequest.getBusinessEntity())
        .creqCreateDate(customerRequest.getCreqCreateDate())
        .creqStatus(customerRequest.getCreqStatus())
        .creqType(customerRequest.getCreqType())
        .customer(customerRequest.getCustomer())
        .ciasResponseDTO(ciasResponseDTO)
        .agen(agenResponseDTO)
        .build();

        return responseDTO;
    }



}
