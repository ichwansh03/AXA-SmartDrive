package com.app.smartdrive.api.controllers.customer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.services.customer.CustomerRequestServiceImpl;

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
    public CustomerRequest create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO){
        return this.customerRequestService.create(customerRequestDTO);
    }



}
