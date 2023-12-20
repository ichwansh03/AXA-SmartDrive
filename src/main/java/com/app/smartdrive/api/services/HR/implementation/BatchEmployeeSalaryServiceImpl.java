package com.app.smartdrive.api.services.HR.implementation;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalaryId;
import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchEmployeeSalaryServiceImpl implements BatchEmployeeSalaryService {
    
    private final BatchEmployeeSalaryRepository batchEmployeeSalaryRepository;

    private final EmployeesRepository employeesRepository;
    
    @Override
    public BatchEmployeeSalary createOne(Long id) {
        Employees employees = employeesRepository.findById(id).get();
        BigDecimal salary = calculateTotalCommission(employees.getEmployeeSalaryDetails()).add(employees.getEmpNetSalary());
        BatchEmployeeSalary batchEmployeeSalary = BatchEmployeeSalary.builder()
                .besaEmpEntityid(id)
                .besaCreatedDate(LocalDate.now())
                .employees(employees)
                .besaStatus(employees.getEmpStatus())
                .besaAccountNumber(employees.getEmpAccountNumber())
                .besaTotalSalary(salary)
                .build();

        return batchEmployeeSalaryRepository.save(batchEmployeeSalary);

    }

    @Override
    public List<BatchEmployeeSalary> getAllTransNull() {
        return batchEmployeeSalaryRepository.findAllByBesaPatrTrxno(null);
    }


    public BigDecimal calculateTotalCommission(List<EmployeeSalaryDetail> salaryDetails) {
        return BigDecimal.valueOf(salaryDetails.stream()
                .filter(detail -> detail.getEmsaName().startsWith("komisi"))
                .mapToDouble(detail -> detail.getEmsaSubtotal().doubleValue())
                .sum());
    }

    @Override
    public List<EmployeeSalaryDetail> getAllCommission(Long besaEmpEntityId, LocalDate besaCreateDate) {
        List<EmployeeSalaryDetail> result = new ArrayList<>();


        // Mendapatkan BatchEmployeeSalary berdasarkan besa_emp_entityid dan besa_create_date
        Optional<BatchEmployeeSalary> optionalBatchEmployeeSalary = batchEmployeeSalaryRepository
                .findByBesaEmpEntityidAndBesaCreatedDate(besaEmpEntityId, besaCreateDate);

        if (optionalBatchEmployeeSalary.isPresent()) {
            BatchEmployeeSalary batchEmployeeSalary = optionalBatchEmployeeSalary.get();
            Employees employees = batchEmployeeSalary.getEmployees();

                // Mendapatkan EmployeeSalaryDetails dari Employees
                List<EmployeeSalaryDetail> salaryDetails = batchEmployeeSalary.getEmployees().getEmployeeSalaryDetails();


                // Filter komisi (memulai dengan "komisi")
                List<EmployeeSalaryDetail> commissionDetails = salaryDetails.stream()
                        .filter(detail -> detail.getEmsaName().startsWith("komisi"))
                        .collect(Collectors.toList());

                // Buat objek BatchEmployeeSalaryDetail untuk setiap komisi
                for (EmployeeSalaryDetail commissionDetail : commissionDetails) {
                    EmployeeSalaryDetail batchEmployeeSalaryDetail = new EmployeeSalaryDetail();
                    batchEmployeeSalaryDetail.setEmsaName(commissionDetail.getEmsaName());
                    batchEmployeeSalaryDetail.setEmsaSubtotal(commissionDetail.getEmsaSubtotal());
                    batchEmployeeSalaryDetail.setEmployees(employees);
                    result.add(batchEmployeeSalaryDetail);
                }

                // Tambahkan net salary
                EmployeeSalaryDetail netSalaryDetail = new EmployeeSalaryDetail();
                netSalaryDetail.setEmsaName("Net Salary");
                netSalaryDetail.setEmsaSubtotal(batchEmployeeSalary.getEmployees().getEmpNetSalary());
                netSalaryDetail.setEmployees(employees);
                result.add(netSalaryDetail);

        }

        return result;
    }


    @Override
    public BatchEmployeeSalary getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<BatchEmployeeSalary> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public BatchEmployeeSalary save(BatchEmployeeSalary t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }


}
