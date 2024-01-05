package com.app.smartdrive.api.repositories.HR;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.payment.Response.PaymentTransactions.EmployeeSalaryHistoryDtoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;


@Repository
public interface BatchEmployeeSalaryRepository extends JpaRepository<BatchEmployeeSalary, Long> {
    List<BatchEmployeeSalary> findAllByBesaPatrTrxno(String besaPatrTrxno);

    Optional<BatchEmployeeSalary> findByBesaEmpEntityidAndBesaCreatedDate(Long besaEmpEntityId, LocalDate besaCreateDate);

    Boolean existsByBesaEmpEntityidAndBesaCreatedDate(Long besaEmpEntityId, LocalDate besaCreateDate);



    @Query(value = "SELECT TOP(1) * from hr.batch_employee_salary WHERE besa_account_number=:besa_account_number " +
            "AND besa_paid_date IS NULL ORDER BY besa_created_date ASC", nativeQuery = true)
    BatchEmployeeSalary findBesaAccountNumber(String besa_account_number);

    @Query(value = "SELECT COUNT(*) from hr.batch_employee_salary where besa_paid_date IS NULL ", nativeQuery = true)
    int countBesaPatrTrxno();

    @Query(value = "SELECT * from hr.batch_employee_salary where besa_paid_date IS NOT NULL", nativeQuery = true)
    List<BatchEmployeeSalary> listEmployeePaidSalary();
}
