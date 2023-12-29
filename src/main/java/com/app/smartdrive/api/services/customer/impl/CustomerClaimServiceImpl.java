package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.ClaimRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CloseRequestDTO;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.customer.CustomerClaimRepository;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerClaimServiceImpl implements CustomerClaimService {

    private final CustomerClaimRepository customerClaimRepository;

    private final CustomerRequestRepository customerRequestRepository;

    @Override
    public CustomerClaim createNewClaim(CustomerRequest customerRequest) {

        CustomerClaim newCustomerClaim = CustomerClaim.builder()
                .cuclEventPrice(new BigDecimal(0))
                .cuclSubtotal(new BigDecimal(0))
                .cuclEvents(0)
                .cuclCreqEntityId(customerRequest.getCreqEntityId())
                .customerRequest(customerRequest)
                .build();

        log.info("CustomerClaimImpl::createNewClaim, create new customer claim");
        return newCustomerClaim;
    }

    @Transactional(readOnly = true)
    @Override
    public ClaimResponseDTO getCustomerClaimById(Long cuclCreqEntityId) {

        CustomerClaim existCustomerClaim = this.customerClaimRepository.findById(cuclCreqEntityId).orElseThrow(
                () -> new EntityNotFoundException("Customer Claim with id " + cuclCreqEntityId + " is not found")
        );


        ClaimResponseDTO claimResponseDTO = TransactionMapper.mapEntityToDto(existCustomerClaim, ClaimResponseDTO.class);


        log.info("CustomerClaimImpl::getCustomerClaimById, find customer claim with id: {}", cuclCreqEntityId);
        return claimResponseDTO;

    }

    @Transactional
    @Override
    public void deleteCustomerClaim(Long cuclCreqEntityId) {
        CustomerClaim existCustomerClaim = this.customerClaimRepository.findById(cuclCreqEntityId).orElseThrow(
                () -> new EntityNotFoundException("Customer Claim with id " + cuclCreqEntityId + " is not found")
        );


        this.customerClaimRepository.delete(existCustomerClaim);
        log.info("CustomerClaimImpl::deleteCustomerClaim, delete customer claim by id: {}", cuclCreqEntityId);
    }

    @Transactional
    @Override
    public CustomerResponseDTO claimPolis(ClaimRequestDTO claimRequestDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(claimRequestDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Request with id " + claimRequestDTO.getCreqEntityId() + " is not found")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.CLAIM);
        existCustomerRequest.setCreqModifiedDate(LocalDateTime.now());


        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerRequest);
        log.info("CustomerClaimServiceImpl::updateCustomerClaim by ID {}", savedCustomerRequest.getCreqEntityId());
        return TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
    }

    @Transactional
    @Override
    public CustomerResponseDTO closePolis(CloseRequestDTO closeRequestDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(closeRequestDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Request with id " + closeRequestDTO.getCreqEntityId() + " is not found")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.CLOSE);
        existCustomerRequest.setCreqModifiedDate(LocalDateTime.now());

        CustomerClaim customerClaim = existCustomerRequest.getCustomerClaim();
        customerClaim.setCuclReason(closeRequestDTO.getCuclReason());
        customerClaim.setCuclCreateDate(LocalDateTime.now());

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerRequest);
        log.info("CustomerClaimServiceImpl::closePolis successfully close polis");
        return TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
    }

    @Override
    public void calculateSubtotalAndEventPrice(CustomerRequest customerRequest, Double paid, Double tax){
        CustomerClaim customerClaim = customerRequest.getCustomerClaim();


        BigDecimal eventPrice = BigDecimal.valueOf(paid);
        eventPrice = eventPrice.add(BigDecimal.valueOf(tax));
        BigDecimal currentPrice = customerClaim.getCuclEventPrice();

        BigDecimal totalEventPrice = currentPrice.add(eventPrice);


        customerClaim.setCuclEvents(customerClaim.getCuclEvents() + 1);
        customerClaim.setCuclSubtotal(BigDecimal.valueOf(paid));
        customerClaim.setCuclEventPrice(customerClaim.getCuclEventPrice().add(BigDecimal.valueOf(paid)));

        this.customerRequestRepository.save(customerClaim.getCustomerRequest());
    }
}
