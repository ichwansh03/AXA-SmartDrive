package com.app.smartdrive.api.repositories.customer;

import com.app.smartdrive.api.entities.customer.CustomerClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerClaimRepository extends JpaRepository<CustomerClaim, Long> {
}
