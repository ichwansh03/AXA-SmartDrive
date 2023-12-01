package com.app.smartdrive.api.repositories.customer;

import com.app.smartdrive.api.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.customer.CustomerRequest;

import java.util.List;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long>{
    Page<CustomerRequest> findByCustomer(User customer, Pageable pageable);
}
