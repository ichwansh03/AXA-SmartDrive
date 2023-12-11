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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerClaimServiceImpl implements CustomerClaimService {

    private final CustomerClaimRepository customerClaimRepository;

    private final CustomerRequestRepository customerRequestRepository;

    @Override
    public CustomerClaim createNewClaim(CustomerRequest customerRequest) {
        CustomerClaim newCustomerClaim = CustomerClaim.builder()
                .cuclEventPrice(0.0)
                .cuclSubtotal(0.0)
                .cuclEvents(0)
                .cuclCreqEntityid(customerRequest.getCreqEntityId())
                .customerRequest(customerRequest)
                .build();

        return newCustomerClaim;
    }

    @Transactional(readOnly = true)
    @Override
    public ClaimResponseDTO getCustomerClaimById(Long cuclCreqEntityId) {
        CustomerClaim existCustomerClaim = this.customerClaimRepository.findById(cuclCreqEntityId).orElseThrow(
                () -> new EntityNotFoundException("Customer Claim dengan id " + cuclCreqEntityId + " tidak ditemukan")
        );


        return ClaimResponseDTO.builder()
                .cuclCreqEntityId(existCustomerClaim.getCuclCreqEntityid())
                .cuclCreateDate(existCustomerClaim.getCuclCreateDate())
                .cuclReason(existCustomerClaim.getCuclReason())
                .cuclEventPrice(existCustomerClaim.getCuclEventPrice())
                .cuclSubtotal(existCustomerClaim.getCuclSubtotal())
                .build();

    }

    @Transactional
    @Override
    public void deleteCustomerClaim(Long cuclCreqEntityId) {
        CustomerClaim existCustomerClaim = this.customerClaimRepository.findById(cuclCreqEntityId).orElseThrow(
                () -> new EntityNotFoundException("Customer Claim dengan id " + cuclCreqEntityId + " tidak ditemukan")
        );

        this.customerClaimRepository.delete(existCustomerClaim);
    }

    @Transactional
    @Override
    public CustomerResponseDTO updateCustomerClaim(ClaimRequestDTO claimRequestDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(claimRequestDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Claim dengan id " + claimRequestDTO.getCreqEntityId() + " tidak ditemukan")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.CLAIM);
        existCustomerRequest.setCreqModifiedDate(LocalDateTime.now());

        CustomerClaim existCustomerClaim = existCustomerRequest.getCustomerClaim();

        LocalDateTime cuclCreateDate = existCustomerClaim.getCuclCreateDate();

        if(Objects.isNull(cuclCreateDate)){
            existCustomerClaim.setCuclCreateDate(LocalDateTime.now());
        }


        Double cuclEventPrice = existCustomerClaim.getCuclEventPrice();
        cuclEventPrice += claimRequestDTO.getCuclEventPrice();

        Double cuclSubtotal = existCustomerClaim.getCuclSubtotal();
        cuclSubtotal += claimRequestDTO.getCuclSubtotal();

        int cuclEvents = existCustomerClaim.getCuclEvents();
        cuclEvents += 1;

        existCustomerClaim.setCuclEventPrice(cuclEventPrice);
        existCustomerClaim.setCuclSubtotal(cuclSubtotal);
        existCustomerClaim.setCuclEvents(cuclEvents);

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerClaim.getCustomerRequest());
        return TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
    }

    @Transactional
    @Override
    public CustomerResponseDTO closePolis(CloseRequestDTO closeRequestDTO) {
        CustomerRequest existCustomerRequest = this.customerRequestRepository.findById(closeRequestDTO.getCreqEntityId()).orElseThrow(
                () -> new EntityNotFoundException("Customer Request dengan id " + closeRequestDTO.getCreqEntityId() + " tidak ada")
        );

        existCustomerRequest.setCreqType(EnumCustomer.CreqType.CLOSE);

        CustomerClaim customerClaim = existCustomerRequest.getCustomerClaim();
        customerClaim.setCuclReason(closeRequestDTO.getCuclReason());
        customerClaim.setCuclCreateDate(LocalDateTime.now());

        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(existCustomerRequest);
        return TransactionMapper.mapEntityToDto(savedCustomerRequest, CustomerResponseDTO.class);
    }
}
