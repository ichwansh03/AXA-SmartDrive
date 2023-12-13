package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.customer.impl.CustomerRequestServiceImpl;
import com.app.smartdrive.api.services.master.CarsService;
import com.app.smartdrive.api.services.master.CityService;
import com.app.smartdrive.api.services.master.IntyService;
import com.app.smartdrive.api.services.master.implementation.CarbServiceImpl;
import com.app.smartdrive.api.services.master.implementation.IntyServiceImpl;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserService;
import com.app.smartdrive.api.services.users.implementation.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private IntyService intyService;

    @Autowired
    private CarsService carsService;

    @Autowired
    private EmployeeAreaWorkgroupService employeeAreaWorkgroupService;

    @Autowired
    private CityService cityService;

    @Autowired
    private BusinessEntityService businessEntityService;

    @Autowired
    private CustomerRequestRepository customerRequestRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CustomerRequestServiceImpl customerRequestService;

    @BeforeEach
    void setUp(){
        this.customerRequestRepository.deleteAll();

    }

    @Test
    void getAllCustomersRequest_willSuccess() throws Exception {
        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
        User customer = this.userService.getUserById(2L).get();
        CarSeries carSeries = this.carsService.getById(1L);
        Cities cities = this.cityService.getById(2L);
        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);

        List<CustomerRequest> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
            Long entityId = businessEntity.getEntityId();

            CustomerRequest customerRequest = CustomerRequest.builder()
                    .creqEntityId(entityId)
                    .businessEntity(businessEntity)
                    .customer(customer)
                    .employeeAreaWorkgroup(eawag)
                    .build();

            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
                    .ciasPoliceNumber("B 123" + i + " CBD")
                    .ciasYear("2023")
                    .carSeries(carSeries)
                    .city(cities)
                    .insuranceType(insuranceType)
                    .ciasCreqEntityid(entityId)
                    .customerRequest(customerRequest)
                    .build();

            customerRequest.setCustomerInscAssets(customerInscAssets);

            CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(customerRequest);
            list.add(savedCustomerRequest);
        }


        mockMvc.perform(
                get("/customer/service/request")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            Page<CustomerResponseDTO> response= objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals(3, response.getTotalElements());
        });





    }
}










