package com.app.smartdrive.api.controllers.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.dto.user.BussinessEntityResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.CustomerInscExtend;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.HR.EmployeeAreaWorkgroupRepository;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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

}