package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.ClaimRequestDTO;
import com.app.smartdrive.api.dto.customer.request.CloseRequestDTO;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.mapper.TransactionMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

public interface CustomerClaimService {
    public CustomerClaim createNewClaim(CustomerRequest customerRequest);

    public CustomerClaim getCustomerClaimById(Long cuclCreqEntityId);

    public void deleteCustomerClaim(Long cuclCreqEntityId);

    public CustomerRequest claimPolis(ClaimRequestDTO claimRequestDTO);

    public CustomerRequest closePolis(CloseRequestDTO closeRequestDTO);

    public void calculateSubtotalAndEventPrice(CustomerRequest customerRequest, Double paid, Double tax);
}
