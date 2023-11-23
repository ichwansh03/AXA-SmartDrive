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

    private final IntyRepository intyRepository;

    private final TemiRepository temiRepository;

    @GetMapping
    public List<CustomerRequest> getAll(){
        return this.customerRequestService.get();
    }

    @PostMapping("/tengku")
    public void createTemplate(
        @RequestParam("temiName1") String name1,
        @RequestParam("temiName2") String name2,
        @RequestParam("temiName3") String name3
    ){
        InsuranceType existInsuranceType = this.intyRepository.findById("medic").get();

        TemplateInsurancePremi temp1 = new TemplateInsurancePremi();
        temp1.setTemiName(name1);
        temp1.setTemiType("Category");
        temp1.setInsuranceType(existInsuranceType);

        TemplateInsurancePremi temp2 = new TemplateInsurancePremi();
        temp2.setTemiName(name2);
        temp2.setTemiType("Category");
        temp2.setInsuranceType(existInsuranceType);

        TemplateInsurancePremi temp3 = new TemplateInsurancePremi();
        temp3.setTemiName(name3);
        temp3.setTemiType("Category");
        temp3.setInsuranceType(existInsuranceType);

        this.temiRepository.save(temp1);
        this.temiRepository.save(temp2);
        this.temiRepository.save(temp3);

    }

    @PostMapping
    public CustomerRequest create(
        @Valid @RequestParam("client") String client,
         @RequestParam("file") MultipartFile[] files
        ) throws Exception{
        
        ObjectMapper mapper = new ObjectMapper();
        CustomerRequestDTO customerRequestDTO = mapper.readValue(client, CustomerRequestDTO.class);
        return this.customerRequestService.create(customerRequestDTO, files);
    }



}
