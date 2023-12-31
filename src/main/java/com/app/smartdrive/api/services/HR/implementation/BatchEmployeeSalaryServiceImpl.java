package com.app.smartdrive.api.services.HR.implementation;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.smartdrive.api.dto.HR.response.BatchEmployeeSalaryResponseDto;
import com.app.smartdrive.api.dto.HR.response.EmployeeSalaryDetailResponseDto;
import com.app.smartdrive.api.entities.hr.TemplateSalary;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeeSalaryDetailRepository;
import com.app.smartdrive.api.repositories.HR.TemplateSalaryRepository;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.entities.hr.BatchEmployeeSalary;
import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.repositories.HR.BatchEmployeeSalaryRepository;
import com.app.smartdrive.api.services.HR.BatchEmployeeSalaryService;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BatchEmployeeSalaryServiceImpl implements BatchEmployeeSalaryService {

    private final BatchEmployeeSalaryRepository batchEmployeeSalaryRepository;

    private final EmployeesService employeesService;

    private final TemplateSalaryRepository templateSalaryRepository;

    private final SemiRepository semiRepository;

    private final SoOrderRepository soOrderRepository;

    private final EmployeeSalaryDetailRepository employeeSalaryDetailRepository;

    @Override
    @Transactional
    public BatchEmployeeSalary createOneWithDetails(Long id) {
        Employees employees = employeesService.getById(id);

        // Check if salary already exists for the current date
        LocalDate currentDate = LocalDate.now();
        if (batchEmployeeSalaryRepository.existsByBesaEmpEntityidAndBesaCreatedDate(id, currentDate)) {
            throw new IllegalStateException("EmployeeSalary already exists for the current date");
        }

        // Generate salary details
        List<TemplateSalary> templateSalaries = templateSalaryRepository.findAll();
        List<EmployeeSalaryDetail> salaryDetails = new ArrayList<>();

        // Find service and related premi
        ServiceOrders serviceOrders = soOrderRepository.findBySeroIdLikeAndEmployees_EawgEntityid(id);
        Long services = (serviceOrders != null && serviceOrders.getServices() != null) ? serviceOrders.getServices().getServId() : null;

        if (services != null) {
            Optional<ServicePremi> semiID = semiRepository.findById(services);
            BigDecimal servicePremi = semiID.get().getSemiPremiDebet();

            for (TemplateSalary templateSalary : templateSalaries) {
                EmployeeSalaryDetail salaryDetail = new EmployeeSalaryDetail();
                salaryDetail.setEmsaEmpEntityid(employees.getEmpEntityid());
                salaryDetail.setEmsaCreateDate(currentDate);
                salaryDetail.setEmsaName(templateSalary.getTesalName());

                BigDecimal rateMin = templateSalary.getTesalRateMin();
                BigDecimal rateMax = templateSalary.getTesalRateMax();

                BigDecimal calculatedSubtotal;

                if (Objects.isNull(rateMin)) {
                    calculatedSubtotal = servicePremi;
                } else if ("FAM".equals(employees.getEmpJobCode()) && Objects.nonNull(rateMax)) {
                    calculatedSubtotal = servicePremi.multiply(rateMax);
                } else {
                    calculatedSubtotal = servicePremi.multiply(rateMin);
                }

                salaryDetail.setEmsaSubtotal(calculatedSubtotal);
                salaryDetail.setEmployees(employees);
                salaryDetail = employeeSalaryDetailRepository.save(salaryDetail);
                salaryDetails.add(salaryDetail);
            }
        }

        // Calculate total salary
        BigDecimal totalSalary;
        if (services != null) {
            totalSalary = calculateTotalCommission(salaryDetails).add(employees.getEmpNetSalary());
        } else {
            totalSalary = employees.getEmpNetSalary();
        }

        // Create BatchEmployeeSalary
        BatchEmployeeSalary batchEmployeeSalary = BatchEmployeeSalary.builder()
                .besaEmpEntityid(id)
                .besaCreatedDate(currentDate)
                .employees(employees)
                .besaStatus(employees.getEmpStatus())
                .besaAccountNumber(employees.getEmpAccountNumber())
                .besaTotalSalary(totalSalary)
                .besaModifiedDate(LocalDateTime.now())
                .build();

        return batchEmployeeSalaryRepository.save(batchEmployeeSalary);
    }

    public BigDecimal calculateTotalCommission(List<EmployeeSalaryDetail> salaryDetails) {
        return BigDecimal.valueOf(salaryDetails.stream()
                .filter(detail -> detail.getEmsaName().startsWith("komisi"))
                .mapToDouble(detail -> detail.getEmsaSubtotal().doubleValue())
                .sum());
    }




    @Override
    @Transactional
    public List<BatchEmployeeSalary> getAllTransNull() {
        return batchEmployeeSalaryRepository.findAllByBesaPatrTrxno(null);
    }


    @Override
    public List<EmployeeSalaryDetailResponseDto> getAllCommission(Long besaEmpEntityId, LocalDate besaCreateDate) {
        List<EmployeeSalaryDetail> result = new ArrayList<>();

        // Mendapatkan BatchEmployeeSalary berdasarkan besa_emp_entityid dan besa_create_date
        Optional<BatchEmployeeSalary> optionalBatchEmployeeSalary = batchEmployeeSalaryRepository
                .findByBesaEmpEntityidAndBesaCreatedDate(besaEmpEntityId, besaCreateDate);

        if (optionalBatchEmployeeSalary.isPresent()) {
            BatchEmployeeSalary batchEmployeeSalary = optionalBatchEmployeeSalary.get();
            Employees employees = batchEmployeeSalary.getEmployees();

            // Mendapatkan EmployeeSalaryDetails dari Employees
            List<EmployeeSalaryDetail> salaryDetails = batchEmployeeSalary.getEmployees().getEmployeeSalaryDetails();

            // Filter komisi (memulai dengan "komisi") dan emsacreatedate yang sama
            List<EmployeeSalaryDetail> commissionDetails = salaryDetails.stream()
                    .filter(detail -> detail.getEmsaName().startsWith("komisi") && detail.getEmsaCreateDate().equals(besaCreateDate))
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

        return TransactionMapper.mapEntityListToDtoList(result, EmployeeSalaryDetailResponseDto.class);
    }


    @Override
    public List<BatchEmployeeSalaryResponseDto> getAll(){
        Sort sort = Sort.by(
                Sort.Order.asc("besaPatrTrxno").nullsFirst()
        );
        List<BatchEmployeeSalary> batchEmployeeSalaries = batchEmployeeSalaryRepository.findAll(sort);
        return TransactionMapper.mapEntityListToDtoList(batchEmployeeSalaries, BatchEmployeeSalaryResponseDto.class);
    }



}
