package com.app.smartdrive.api.services.HR.implementation;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalaryId;
import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.partner.BatchPartnerInvoice;
import com.app.smartdrive.api.entities.partner.BpinStatus;
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
        BatchEmployeeSalaryId batchEmployeeSalaryId = new BatchEmployeeSalaryId();
        batchEmployeeSalaryId.setBesaEmpEntityid(id);
        batchEmployeeSalaryId.setBesaCreatedDate(LocalDateTime.now());
        Double salary = calculateTotalSubtotal(employees.getEmployeeSalaryDetails())+employees.getEmpNetSalary();
        BatchEmployeeSalary batchEmployeeSalary = BatchEmployeeSalary.builder()
        .batchEmployeeSalaryId(batchEmployeeSalaryId)
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


    public Double calculateTotalSubtotal(List<EmployeeSalaryDetail> employeeSalaryDetails) {
        return employeeSalaryDetails.stream()
                .map(EmployeeSalaryDetail::getEmsaSubtotal)
                .reduce(0.0, Double::sum);
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
