package com.app.smartdrive.api.repositories.HR;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;


@Repository
public interface BatchEmployeeSalaryRepository extends JpaRepository<BatchEmployeeSalary, Long> {
    List<BatchEmployeeSalary> findAllByBesaPatrTrxno(String besaPatrTrxno);

    Optional<BatchEmployeeSalary> findByBesaEmpEntityidAndBesaCreatedDate(Long besaEmpEntityId, LocalDate besaCreateDate);

    Boolean existsByBesaEmpEntityidAndBesaCreatedDate(Long besaEmpEntityId, LocalDate besaCreateDate);



    @Query(value = "SELECT TOP(1) * from hr.batch_employee_salary WHERE besa_account_number=:besa_account_number " +
            "AND besa_paid_date IS NULL ORDER BY besa_created_date ASC", nativeQuery = true)
    BatchEmployeeSalary findBesaAccountNumber(String besa_account_number);

    @Query(value = "SELECT COUNT(*) from hr.batch_employee_salary where besa_paid_date IS NULL " +
            "AND besa_account_number=:besa_account_number ", nativeQuery = true)
    int countBesaPatrTrxno(String besa_account_number);

    @Query(value = "SELECT * from hr.batch_employee_salary where besa_paid_date IS NOT NULL", nativeQuery = true)
    List<BatchEmployeeSalary> listEmployeePaidSalary();
}
