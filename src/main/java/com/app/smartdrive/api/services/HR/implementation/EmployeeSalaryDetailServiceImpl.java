package com.app.smartdrive.api.services.HR.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.app.smartdrive.api.services.HR.EmployeesService;
import org.springframework.stereotype.Service;
import com.app.smartdrive.api.entities.hr.EmployeeSalaryDetail;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.TemplateSalary;
import com.app.smartdrive.api.entities.service_order.ServiceOrders;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.repositories.HR.EmployeeSalaryDetailRepository;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.HR.TemplateSalaryRepository;
import com.app.smartdrive.api.repositories.service_orders.SemiRepository;
import com.app.smartdrive.api.repositories.service_orders.SoOrderRepository;
import com.app.smartdrive.api.services.HR.EmployeeSalaryDetailService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeSalaryDetailServiceImpl implements EmployeeSalaryDetailService {
    
    private final TemplateSalaryRepository templateSalaryRepository;

    private final SemiRepository semiRepository;

    private final SoOrderRepository soOrderRepository;

    private final EmployeeSalaryDetailRepository employeeSalaryDetailRepository;

    private final EmployeesService employeesService;


    @Override
    @Transactional
    public List<EmployeeSalaryDetail> generateSalaryDetails(Long entityId) {
        List<TemplateSalary> templateSalaries = templateSalaryRepository.findAll();
        List<EmployeeSalaryDetail> salaryDetails = new ArrayList<>();

//         BigDecimal totalPremi = new BigDecimal(100000);

        ServiceOrders pl = soOrderRepository.findBySeroIdLikeAndEmployees_EawgEntityid(entityId);

        Long services = pl.getServices().getServId();
        Optional<ServicePremi> semiID = semiRepository.findById(services);

        BigDecimal servicePremi = semiID.get().getSemiPremiDebet();

        Employees employees = employeesService.getById(entityId);

        for (TemplateSalary templateSalary : templateSalaries) {
            EmployeeSalaryDetail salaryDetail = new EmployeeSalaryDetail();
            salaryDetail.setEmsaEmpEntityid(employees.getEmpEntityid());
            salaryDetail.setEmsaCreateDate(LocalDate.now());
            salaryDetail.setEmsaName(templateSalary.getTesalName());
            
            BigDecimal rateMin = templateSalary.getTesalRateMin();
            BigDecimal rateMax = templateSalary.getTesalRateMax();

            BigDecimal calculatedSubtotal ;

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
        return salaryDetails;
    }

    
}
