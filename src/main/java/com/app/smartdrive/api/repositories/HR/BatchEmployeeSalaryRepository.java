package com.app.smartdrive.api.repositories.HR;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;


@Repository
public interface BatchEmployeeSalaryRepository extends JpaRepository<BatchEmployeeSalary, Long> {
    List<BatchEmployeeSalary> findAllByBesaPatrTrxno(String besaPatrTrxno);
}
