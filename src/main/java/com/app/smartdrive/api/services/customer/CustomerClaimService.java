package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.ClaimRequestDTO;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;

import java.time.LocalDateTime;
import java.util.Objects;

public interface CustomerClaimService {
    public CustomerClaim createNewClaim(CustomerRequest customerRequest);

    public ClaimResponseDTO getCustomerClaimById(Long cuclCreqEntityId);

    public void deleteCustomerClaim(Long cuclCreqEntityId);

    public CustomerResponseDTO updateCustomerClaim(ClaimRequestDTO claimRequestDTO);
}
