package com.app.smartdrive.api.services.HR.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    private final EmployeesRepository employeesRepository;


    @Override
    @Transactional
    public List<EmployeeSalaryDetail> generateSalaryDetails(Long entityId) {
        List<TemplateSalary> templateSalaries = templateSalaryRepository.findAll();
        List<EmployeeSalaryDetail> salaryDetails = new ArrayList<>();

        Double totalPremi = 1000000.0;

        // List<ServiceOrders> listServiceOrders = soOrderRepository.findByServices_Users_UserEntityId(entityId);
        
        // List<Long> listEntityId = listServiceOrders.stream().map(so -> so.getEmployees().getEawgEntityid()).toList();

        // List<Double> listPremi = services.getServicePremiSet().stream().map(prem -> prem.getSemiPremiDebet()).toList();
        // List<ServicePremi> listServicePremi = semiRepository.findAllBySemiServId(pl.getServices().getServId());

        ServiceOrders pl = soOrderRepository.findBySeroIdLikeAndEmployees_EawgEntityid(entityId);

        Long services = pl.getServices().getServId();
        Optional<ServicePremi> semiID = semiRepository.findById(services);
        
        BigDecimal servicePremi = semiID.get().getSemiPremiDebet();

        Employees employees = employeesRepository.findById(entityId).get();

        for (TemplateSalary templateSalary : templateSalaries) {
            EmployeeSalaryDetail salaryDetail = new EmployeeSalaryDetail();
            salaryDetail.setEmsaEmpEntityid(employees.getEmpEntityid());
            salaryDetail.setEmsaCreateDate(LocalDate.now());
            salaryDetail.setEmsaName(templateSalary.getTesalName());

            
            Double rateMin = templateSalary.getTesalRateMin();
            // Double rateMax = templateSalary.getTesalRateMax();
            // Double nominal = templateSalary.getTesalNominal();

            
            BigDecimal calculatedSubtotal ;
            if(Objects.isNull(rateMin)){
                calculatedSubtotal = servicePremi;
            }else{
             calculatedSubtotal = servicePremi.multiply(BigDecimal.valueOf(rateMin));
            }


            salaryDetail.setEmsaSubtotal(calculatedSubtotal.doubleValue());
            salaryDetail.setEmployees(employees);
            salaryDetail = employeeSalaryDetailRepository.save(salaryDetail);
            salaryDetails.add(salaryDetail);

        }

        
        return salaryDetails;
    }
    
    
    @Override
    public EmployeeSalaryDetail getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<EmployeeSalaryDetail> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public EmployeeSalaryDetail save(EmployeeSalaryDetail t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    
}
