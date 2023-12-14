package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
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
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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

    //test

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

        for (int i = 0; i < 4; i++) {
            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
            Long entityId = businessEntity.getEntityId();

            CustomerRequest customerRequest = CustomerRequest.builder()
                    .creqEntityId(entityId)
                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
                    .businessEntity(businessEntity)
                    .customer(customer)
                    .employeeAreaWorkgroup(eawag)
                    .creqAgenEntityid(eawag.getEawgId())
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

            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
            list.add(savedCustomerRequest);
        }


        mockMvc.perform(
                get("/customer/service/request")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.totalElements", is(list.size())))
                .andDo(print());





    }

    @Test
    void getAllCustomersRequest_willFailed() throws Exception {
        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
        User customer = this.userService.getUserById(2L).get();
        CarSeries carSeries = this.carsService.getById(1L);
        Cities cities = this.cityService.getById(2L);
        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);

        List<CustomerRequest> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
            Long entityId = businessEntity.getEntityId();

            CustomerRequest customerRequest = CustomerRequest.builder()
                    .creqEntityId(entityId)
                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
                    .businessEntity(businessEntity)
                    .customer(customer)
                    .employeeAreaWorkgroup(eawag)
                    .creqAgenEntityid(eawag.getEawgId())
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

            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
            list.add(savedCustomerRequest);
        }


        mockMvc.perform(
                        get("/customer/service/salah/request")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createCustomerRequest_willSuccess() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        Long[] arr = {7L,8L,9L};

        CiasDTO ciasDTO = CiasDTO.builder()
                .ciasPoliceNumber("B 123457 CBD")
                .ciasYear("2007")
                .ciasIsNewChar('Y')
                .ciasPaidType("CASH")
                .ciasCarsId(1L)
                .ciasCityId(1L)
                .ciasIntyName("Comprehensive")
                .ciasStartdate("2023-09-01 15:02:00")
                .currentPrice(150_000_000.00)
                .cuexIds(arr)
                .build();

        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
                .customerId(2L)
                .agenId(8L)
                .employeeId(1L)
                .ciasDTO(ciasDTO)
                .build();

        mockMvc.perform(
                multipart("/customer/service/request")
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("client", objectMapper.writeValueAsString(customerRequestDTO))
                )
                .andExpect(status().isCreated()
        );
    }
}










