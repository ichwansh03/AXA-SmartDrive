package com.app.smartdrive.api.services.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerRequestServiceImpl {
    private final CustomerRequestRepository customerRequestRepository;

    private final BusinessEntityRepo businessEntityRepo;

    public List<CustomerRequest> get(){
        return this.customerRequestRepository.findAll();
    }

    public CustomerRequest create(@Valid CustomerRequestDTO customerRequestDTO) {
        BusinessEntity existEntity = this.businessEntityRepo.findById(customerRequestDTO.getCreqEntityId()).get();
        User entityUser = existEntity.getUser();


        CustomerRequest newCustomer = CustomerRequest.builder()
        .businessEntity(existEntity)
        .customer(entityUser)
        .creqCreateDate(LocalDateTime.now())
        .creqStatus(EnumCustomer.CreqStatus.OPEN)
        .creqType(EnumCustomer.CreqType.POLIS)
        .build();

        return this.customerRequestRepository.save(newCustomer);
    }
}
