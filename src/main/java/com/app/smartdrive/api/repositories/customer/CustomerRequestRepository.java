package com.app.smartdrive.api.repositories.customer;

import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.customer.CustomerRequest;

import java.util.List;

@Repository
public interface CustomerRequestRepository extends JpaRepository<CustomerRequest, Long>{
    Page<CustomerRequest> findByCustomer(User customer, Pageable pageable);

    Page<CustomerRequest> findByCustomerAndCreqTypeAndCreqStatus(User customer, Pageable pageable, EnumCustomer.CreqType creqType, EnumCustomer.CreqStatus creqStatus);

    Page<CustomerRequest> findByCustomerAndCreqStatus(User customer, Pageable pageable, EnumCustomer.CreqStatus creqStatus);

    Page<CustomerRequest> findByEmployeeAreaWorkgroupAndCreqTypeAndCreqStatus(EmployeeAreaWorkgroup employeeAreaWorkgroup, Pageable pageable, EnumCustomer.CreqType creqType, EnumCustomer.CreqStatus creqStatus);

    Page<CustomerRequest> findByEmployeeAreaWorkgroupAndCreqStatus(EmployeeAreaWorkgroup employeeAreaWorkgroup, Pageable pageable, EnumCustomer.CreqStatus creqStatus);

    Page<CustomerRequest> findByCreqTypeAndCreqStatus(Pageable pageable, EnumCustomer.CreqType creqType, EnumCustomer.CreqStatus creqStatus);

    Page<CustomerRequest> findByCreqStatus(Pageable pageable, EnumCustomer.CreqStatus creqStatus);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE CustomerRequest creq SET creq.creqType = ?1 WHERE creq.creqEntityId = ?2")
    int updateCreqType(EnumCustomer.CreqType creqType, Long creqId);
}
