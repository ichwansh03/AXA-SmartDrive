//package com.app.smartdrive.api.controllers.customerService.customer;
//
//import com.app.smartdrive.api.dto.customer.request.CiasDTO;
//import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
//import com.app.smartdrive.api.dto.customer.request.UpdateCustomerRequestDTO;
//import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
//import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
//import com.app.smartdrive.api.entities.customer.CustomerRequest;
//import com.app.smartdrive.api.entities.customer.EnumCustomer;
//import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
//import com.app.smartdrive.api.entities.master.CarSeries;
//import com.app.smartdrive.api.entities.master.Cities;
//import com.app.smartdrive.api.entities.master.InsuranceType;
//import com.app.smartdrive.api.entities.users.BusinessEntity;
//import com.app.smartdrive.api.entities.users.User;
//import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
//import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
//import com.app.smartdrive.api.services.customer.impl.CustomerRequestServiceImpl;
//import com.app.smartdrive.api.services.master.CarsService;
//import com.app.smartdrive.api.services.master.CityService;
//import com.app.smartdrive.api.services.master.IntyService;
//import com.app.smartdrive.api.services.users.BusinessEntityService;
//import com.app.smartdrive.api.services.users.UserService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.request.RequestPostProcessor;
//
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CustomerServiceControllerIntegrationTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private IntyService intyService;
//
//    @Autowired
//    private CarsService carsService;
//
//    @Autowired
//    private EmployeeAreaWorkgroupService employeeAreaWorkgroupService;
//
//    @Autowired
//    private CityService cityService;
//
//    @Autowired
//    private BusinessEntityService businessEntityService;
//
//    @Autowired
//    private CustomerRequestRepository customerRequestRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    private CustomerRequestServiceImpl customerRequestService;
//
//    //test
//
//    @BeforeEach
//    void setUp(){
//        this.customerRequestRepository.deleteAll();
//    }
//
//    @Test
//    void getAllCustomersRequest_willSuccess() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        List<CustomerRequest> list = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//            Long entityId = businessEntity.getEntityId();
//
//            CustomerRequest customerRequest = CustomerRequest.builder()
//                    .creqEntityId(entityId)
//                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                    .businessEntity(businessEntity)
//                    .customer(customer)
//                    .employeeAreaWorkgroup(eawag)
//                    .creqAgenEntityid(eawag.getEawgId())
//                    .build();
//
//            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                    .ciasPoliceNumber("B 123" + i + " CBD")
//                    .ciasYear("2023")
//                    .carSeries(carSeries)
//                    .city(cities)
//                    .insuranceType(insuranceType)
//                    .ciasCreqEntityid(entityId)
//                    .customerRequest(customerRequest)
//                    .build();
//
//            customerRequest.setCustomerInscAssets(customerInscAssets);
//
//            CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(customerRequest);
//            list.add(savedCustomerRequest);
//        }
//
//
//        mockMvc.perform(
//                get("/customer/service/request")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", hasSize(3)))
//                .andExpect(jsonPath("$.totalElements", is(list.size())))
//                .andDo(print());
//
//
//
//
//
//    }
//
//    @Test
//    void getAllCustomersRequest_willFailed() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        List<CustomerRequest> list = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//            Long entityId = businessEntity.getEntityId();
//
//            CustomerRequest customerRequest = CustomerRequest.builder()
//                    .creqEntityId(entityId)
//                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                    .businessEntity(businessEntity)
//                    .customer(customer)
//                    .employeeAreaWorkgroup(eawag)
//                    .creqAgenEntityid(eawag.getEawgId())
//                    .build();
//
//            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                    .ciasPoliceNumber("B 123" + i + " CBD")
//                    .ciasYear("2023")
//                    .carSeries(carSeries)
//                    .city(cities)
//                    .insuranceType(insuranceType)
//                    .ciasCreqEntityid(entityId)
//                    .customerRequest(customerRequest)
//                    .build();
//
//            customerRequest.setCustomerInscAssets(customerInscAssets);
//
//            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
//            list.add(savedCustomerRequest);
//        }
//
//
//        mockMvc.perform(
//                        get("/customer/service/salah/request")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    void createCustomerRequest_willSuccess() throws Exception {
//
//        MockMultipartFile file =
//                new MockMultipartFile(
//                        "file",
//                        "contract.pdf",
//                        MediaType.APPLICATION_PDF_VALUE,
//                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
//
//        Long[] arr = {7L,8L,9L};
//
//        CiasDTO ciasDTO = CiasDTO.builder()
//                .ciasPoliceNumber("B 123457 CBD")
//                .ciasYear("2007")
//                .ciasIsNewChar('Y')
//                .ciasPaidType("CASH")
//                .ciasCarsId(1L)
//                .ciasCityId(1L)
//                .ciasIntyName("Comprehensive")
//                .ciasStartdate("2023-09-01 15:02:00")
//                .currentPrice(150_000_000.00)
//                .cuexIds(arr)
//                .build();
//
//        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
//                .customerId(2L)
//                .agenId(8L)
//                .employeeId(1L)
//                .ciasDTO(ciasDTO)
//                .build();
//
//        mockMvc.perform(
//                multipart("/customer/service/request")
//                        .file(file)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("client", objectMapper.writeValueAsString(customerRequestDTO))
//                )
//                .andExpect(status().isCreated()
//        );
//    }
//
//    @Test
//    void createCustomerRequest_willFailed() throws Exception {
//
//        MockMultipartFile file =
//                new MockMultipartFile(
//                        "file",
//                        "contract.pdf",
//                        MediaType.APPLICATION_PDF_VALUE,
//                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
//
//        Long[] arr = {7L,8L,9L};
//
//        CiasDTO ciasDTO = CiasDTO.builder()
//                .ciasPoliceNumber("B 123457 CBD")
//                .ciasYear("2007")
//                .ciasIsNewChar('Y')
//                .ciasPaidType("CASH")
//                .ciasCarsId(1L)
//                .ciasCityId(20L)
//                .ciasIntyName("Comprehensive")
//                .ciasStartdate("2023-09-01 15:02:00")
//                .currentPrice(150_000_000.00)
//                .cuexIds(arr)
//                .build();
//
//        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
//                .customerId(2L)
//                .agenId(8L)
//                .employeeId(1L)
//                .ciasDTO(ciasDTO)
//                .build();
//
//        mockMvc.perform(
//                        multipart("/customer/service/request")
//                                .file(file)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .param("client", objectMapper.writeValueAsString(customerRequestDTO))
//                )
//                .andExpect(status().isNotFound()
//                );
//    }
//
////    @Test
////    void createCustomerRequestByAgen_willSuccess() throws Exception {
////
////        MockMultipartFile file =
////                new MockMultipartFile(
////                        "file",
////                        "contract.pdf",
////                        MediaType.APPLICATION_PDF_VALUE,
////                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
////
////        ProfileRequestDto profileRequestDto = ProfileRequestDto.builder()
////                .userFullName("Mikel")
////                .userName("mikel123")
////                .userBirthDate(LocalDateTime.now())
////                .userEmail("mikel@gmail.com")
////                .userPassword("")
////                .userNationalId("halo123")
////                .build();
////
////        CreateUserDto createUserDto = CreateUserDto.builder()
////                .profile(profileRequestDto)
////                .build();
////
////
////        Long[] arr = {7L,8L,9L};
////        CiasDTO ciasDTO = CiasDTO.builder()
////                .ciasPoliceNumber("B 123457 CBD")
////                .ciasYear("2007")
////                .ciasIsNewChar('Y')
////                .ciasPaidType("CASH")
////                .ciasCarsId(1L)
////                .ciasCityId(1L)
////                .ciasIntyName("Comprehensive")
////                .ciasStartdate("2023-09-01 15:02:00")
////                .currentPrice(150_000_000.00)
////                .cuexIds(arr)
////                .build();
////
//////        CreateCustomerRequestByAgenDTO customerRequestDTO = CreateCustomerRequestByAgenDTO.builder()
//////                .
//////                .agenId(8L)
//////                .employeeId(1L)
//////                .ciasDTO(ciasDTO)
//////
//////                .build();
////
////        mockMvc.perform(
////                        multipart("/customer/service/request")
////                                .file(file)
////                                .accept(MediaType.APPLICATION_JSON)
////                                .param("client", objectMapper.writeValueAsString(customerRequestDTO))
////                )
////                .andExpect(status().isCreated()
////                );
////    }
////
////    @Test
////    void createCustomerRequestByAgen_willFailed() throws Exception {
////
////        MockMultipartFile file =
////                new MockMultipartFile(
////                        "file",
////                        "contract.pdf",
////                        MediaType.APPLICATION_PDF_VALUE,
////                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
////
////        Long[] arr = {7L,8L,9L};
////
////        CiasDTO ciasDTO = CiasDTO.builder()
////                .ciasPoliceNumber("B 123457 CBD")
////                .ciasYear("2007")
////                .ciasIsNewChar('Y')
////                .ciasPaidType("CASH")
////                .ciasCarsId(1L)
////                .ciasCityId(20L)
////                .ciasIntyName("Comprehensive")
////                .ciasStartdate("2023-09-01 15:02:00")
////                .currentPrice(150_000_000.00)
////                .cuexIds(arr)
////                .build();
////
////        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
////                .customerId(2L)
////                .agenId(8L)
////                .employeeId(1L)
////                .ciasDTO(ciasDTO)
////                .build();
////
////        mockMvc.perform(
////                        multipart("/customer/service/request")
////                                .file(file)
////                                .accept(MediaType.APPLICATION_JSON)
////                                .param("client", objectMapper.writeValueAsString(customerRequestDTO))
////                )
////                .andExpect(status().isNotFound()
////                );
////    }
//
//    @Test
//    void getCustomerRequestById_willSuccess() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//
//        BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//        Long entityId = businessEntity.getEntityId();
//
//        CustomerRequest customerRequest = CustomerRequest.builder()
//                .creqEntityId(entityId)
//                .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                .businessEntity(businessEntity)
//                .customer(customer)
//                .employeeAreaWorkgroup(eawag)
//                .creqAgenEntityid(eawag.getEawgId())
//                .build();
//
//        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                .ciasPoliceNumber("B 1234 CBD")
//                .ciasYear("2023")
//                .carSeries(carSeries)
//                .city(cities)
//                .insuranceType(insuranceType)
//                .ciasCreqEntityid(entityId)
//                .customerRequest(customerRequest)
//                .build();
//
//        customerRequest.setCustomerInscAssets(customerInscAssets);
//
//        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(customerRequest);
//
//        mockMvc.perform(
//                get("/customer/service/request/search")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("creqEntityId", savedCustomerRequest.getCreqEntityId().toString())
//        ).andExpectAll(status().isOk())
//                .andDo(result -> {
//                    CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
//
//                    assertNotNull(response);
//                    assertEquals(savedCustomerRequest.getCreqEntityId(), response.getCreqEntityId());
//                    assertEquals(savedCustomerRequest.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
//                });
//    }
//
//    @Test
//    void getCustomerRequestById_willFailed() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//
//        BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//        Long entityId = businessEntity.getEntityId();
//
//        CustomerRequest customerRequest = CustomerRequest.builder()
//                .creqEntityId(entityId)
//                .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                .businessEntity(businessEntity)
//                .customer(customer)
//                .employeeAreaWorkgroup(eawag)
//                .creqAgenEntityid(eawag.getEawgId())
//                .build();
//
//        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                .ciasPoliceNumber("B 1234 CBD")
//                .ciasYear("2023")
//                .carSeries(carSeries)
//                .city(cities)
//                .insuranceType(insuranceType)
//                .ciasCreqEntityid(entityId)
//                .customerRequest(customerRequest)
//                .build();
//
//        customerRequest.setCustomerInscAssets(customerInscAssets);
//
//        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(customerRequest);
//
//        mockMvc.perform(
//                        get("/customer/service/request/search")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .param("creqEntityId", String.valueOf(1))
//                ).andExpectAll(status().isNotFound())
//                .andDo(print());
//    }
//
//    @Test
//    void getAllUserCustomerRequest_willSuccess() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        List<CustomerRequest> list = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//            Long entityId = businessEntity.getEntityId();
//
//            CustomerRequest customerRequest = CustomerRequest.builder()
//                    .creqEntityId(entityId)
//                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                    .businessEntity(businessEntity)
//                    .customer(customer)
//                    .employeeAreaWorkgroup(eawag)
//                    .creqAgenEntityid(eawag.getEawgId())
//                    .build();
//
//            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                    .ciasPoliceNumber("B 123" + i + " CBD")
//                    .ciasYear("2023")
//                    .carSeries(carSeries)
//                    .city(cities)
//                    .insuranceType(insuranceType)
//                    .ciasCreqEntityid(entityId)
//                    .customerRequest(customerRequest)
//                    .build();
//
//            customerRequest.setCustomerInscAssets(customerInscAssets);
//
//            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
//            list.add(savedCustomerRequest);
//
//            mockMvc.perform(
//                    get("/customer/service/request/customer")
//                            .param("customerId", customer.getUserEntityId().toString())
//                            .accept(MediaType.APPLICATION_JSON)
//            )
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.totalElements", is(list.size())))
//                    .andDo(print());
//        }
//
//    }
//
//    @Test
//    void getAllUserCustomerRequest_willFailed() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        List<CustomerRequest> list = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//            Long entityId = businessEntity.getEntityId();
//
//            CustomerRequest customerRequest = CustomerRequest.builder()
//                    .creqEntityId(entityId)
//                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                    .businessEntity(businessEntity)
//                    .customer(customer)
//                    .employeeAreaWorkgroup(eawag)
//                    .creqAgenEntityid(eawag.getEawgId())
//                    .build();
//
//            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                    .ciasPoliceNumber("B 123" + i + " CBD")
//                    .ciasYear("2023")
//                    .carSeries(carSeries)
//                    .city(cities)
//                    .insuranceType(insuranceType)
//                    .ciasCreqEntityid(entityId)
//                    .customerRequest(customerRequest)
//                    .build();
//
//            customerRequest.setCustomerInscAssets(customerInscAssets);
//
//            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
//            list.add(savedCustomerRequest);
//        }
//            mockMvc.perform(
//                            get("/customer/service/request/customer")
//                                    .param("customerId", String.valueOf(20))
//                                    .accept(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isNotFound())
//                    .andDo(print());
//
//    }
//
//    @Test
//    void getAllAgenCustomerRequest_willSuccess() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        List<CustomerRequest> list = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//            Long entityId = businessEntity.getEntityId();
//
//            CustomerRequest customerRequest = CustomerRequest.builder()
//                    .creqEntityId(entityId)
//                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                    .businessEntity(businessEntity)
//                    .customer(customer)
//                    .employeeAreaWorkgroup(eawag)
//                    .creqAgenEntityid(eawag.getEawgId())
//                    .build();
//
//            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                    .ciasPoliceNumber("B 123" + i + " CBD")
//                    .ciasYear("2023")
//                    .carSeries(carSeries)
//                    .city(cities)
//                    .insuranceType(insuranceType)
//                    .ciasCreqEntityid(entityId)
//                    .customerRequest(customerRequest)
//                    .build();
//
//            customerRequest.setCustomerInscAssets(customerInscAssets);
//
//            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
//            list.add(savedCustomerRequest);
//
//        }
//            mockMvc.perform(
//                            get("/customer/service/request/agen")
//                                    .param("employeeId", eawag.getEawgEntityid().toString())
//                                    .param("arwgCode", eawag.getEawgArwgCode())
//                                    .accept(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.totalElements", is(list.size())))
//                    .andDo(print());
//
//    }
//
//    @Test
//    void getAllAgenCustomerRequest_willFailed() throws Exception {
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        List<CustomerRequest> list = new ArrayList<>();
//
//        for (int i = 0; i < 4; i++) {
//            BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//            Long entityId = businessEntity.getEntityId();
//
//            CustomerRequest customerRequest = CustomerRequest.builder()
//                    .creqEntityId(entityId)
//                    .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                    .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                    .businessEntity(businessEntity)
//                    .customer(customer)
//                    .employeeAreaWorkgroup(eawag)
//                    .creqAgenEntityid(eawag.getEawgId())
//                    .build();
//
//            CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                    .ciasPoliceNumber("B 123" + i + " CBD")
//                    .ciasYear("2023")
//                    .carSeries(carSeries)
//                    .city(cities)
//                    .insuranceType(insuranceType)
//                    .ciasCreqEntityid(entityId)
//                    .customerRequest(customerRequest)
//                    .build();
//
//            customerRequest.setCustomerInscAssets(customerInscAssets);
//
//            CustomerRequest savedCustomerRequest = this.customerRequestRepository.saveAndFlush(customerRequest);
//            list.add(savedCustomerRequest);
//
//            mockMvc.perform(
//                            get("/customer/service/request/agen")
//                                    .param("employeeId", eawag.getEawgEntityid().toString())
//                                    .param("arwgCode", "B3B3LAC")
//                                    .accept(MediaType.APPLICATION_JSON)
//                    )
//                    .andExpect(status().isNotFound())
//                    .andDo(print());
//        }
//
//    }
//
//    @Test
//    void updateCustomerRequest_willSuccess() throws Exception {
//        // create
//        BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//        Long entityId = businessEntity.getEntityId();
//
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        CustomerRequest customerRequest = CustomerRequest.builder()
//                .creqEntityId(entityId)
//                .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                .businessEntity(businessEntity)
//                .customer(customer)
//                .employeeAreaWorkgroup(eawag)
//                .creqAgenEntityid(eawag.getEawgId())
//                .build();
//
//        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                .ciasPoliceNumber("B 1234 CBD")
//                .ciasYear("2023")
//                .carSeries(carSeries)
//                .city(cities)
//                .insuranceType(insuranceType)
//                .ciasCreqEntityid(entityId)
//                .customerRequest(customerRequest)
//                .ciasCurrentPrice(123_000_000.00)
//                .build();
//
//        customerRequest.setCustomerInscAssets(customerInscAssets);
//
//        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(customerRequest);
//
//
//        // DTO update
//        MockMultipartFile file =
//                new MockMultipartFile(
//                        "file",
//                        "contract.pdf",
//                        MediaType.APPLICATION_PDF_VALUE,
//                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
//
//        Long[] arr = {7L,8L,9L};
//
//        CiasDTO ciasDTO = CiasDTO.builder()
//                .ciasPoliceNumber("ANGIE")
//                .ciasYear("2007")
//                .ciasIsNewChar('Y')
//                .ciasPaidType("CASH")
//                .ciasCarsId(1L)
//                .ciasCityId(2L)
//                .ciasIntyName("Comprehensive")
//                .ciasStartdate("2023-09-01 15:02:00")
//                .currentPrice(150_000_000.00)
//                .cuexIds(arr)
//                .build();
//
//        UpdateCustomerRequestDTO updateCustomerRequestDTO = UpdateCustomerRequestDTO.builder()
//                .creqEntityId(savedCustomerRequest.getCreqEntityId())
//                .customerId(2L)
//                .agenId(8L)
//                .employeeId(1L)
//                .ciasDTO(ciasDTO)
//                .build();
//
//        // custom multipart for method put
//        MockMultipartHttpServletRequestBuilder builder =
//                MockMvcRequestBuilders.multipart("/customer/service/request");
//        builder.with(new RequestPostProcessor() {
//            @Override
//            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
//                request.setMethod("PUT");
//                return request;
//            }
//        });
//
//        mockMvc.perform(builder
//                .file(file)
//                .accept(MediaType.APPLICATION_JSON)
//                .param("client", objectMapper.writeValueAsString(updateCustomerRequestDTO))
//        ).andExpectAll(
//                status().isOk()
//        ).andDo(result -> {
//            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
//            });
//
//           assertNotNull(response);
//           assertEquals(updateCustomerRequestDTO.getCreqEntityId(), response.getCreqEntityId());
//           assertEquals(ciasDTO.getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
//           assertEquals(ciasDTO.getCurrentPrice(), response.getCustomerInscAssets().getCiasCurrentPrice());
//
//        });
//
//    }
//
//    @Test
//    void updateCustomerRequest_willFailed() throws Exception {
//        // create
//        BusinessEntity businessEntity = this.businessEntityService.createBusinessEntity();
//        Long entityId = businessEntity.getEntityId();
//
//        InsuranceType insuranceType = this.intyService.getById("Comprehensive");
//        User customer = this.userService.getUserById(2L).get();
//        CarSeries carSeries = this.carsService.getById(1L);
//        Cities cities = this.cityService.getById(2L);
//        EmployeeAreaWorkgroup eawag = this.employeeAreaWorkgroupService.getById(1L);
//
//        CustomerRequest customerRequest = CustomerRequest.builder()
//                .creqEntityId(entityId)
//                .creqStatus(EnumCustomer.CreqStatus.OPEN)
//                .creqType(EnumCustomer.CreqType.FEASIBLITY)
//                .businessEntity(businessEntity)
//                .customer(customer)
//                .employeeAreaWorkgroup(eawag)
//                .creqAgenEntityid(eawag.getEawgId())
//                .build();
//
//        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
//                .ciasPoliceNumber("B 1234 CBD")
//                .ciasYear("2023")
//                .carSeries(carSeries)
//                .city(cities)
//                .insuranceType(insuranceType)
//                .ciasCreqEntityid(entityId)
//                .customerRequest(customerRequest)
//                .ciasCurrentPrice(123_000_000.00)
//                .build();
//
//        customerRequest.setCustomerInscAssets(customerInscAssets);
//
//        CustomerRequest savedCustomerRequest = this.customerRequestRepository.save(customerRequest);
//
//
//        // DTO update
//        MockMultipartFile file =
//                new MockMultipartFile(
//                        "file",
//                        "contract.pdf",
//                        MediaType.APPLICATION_PDF_VALUE,
//                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
//
//        Long[] arr = {7L,8L,9L};
//
//        CiasDTO ciasDTO = CiasDTO.builder()
//                .ciasPoliceNumber("ANGIE")
//                .ciasYear("2007")
//                .ciasIsNewChar('Y')
//                .ciasPaidType("CASH")
//                .ciasCarsId(1L)
//                .ciasCityId(2L)
//                .ciasIntyName("Comprehensive")
//                .ciasStartdate("2023-09-01 15:02:00")
//                .currentPrice(150_000_000.00)
//                .cuexIds(arr)
//                .build();
//
//        UpdateCustomerRequestDTO updateCustomerRequestDTO = UpdateCustomerRequestDTO.builder()
//                .creqEntityId(220L)
//                .customerId(2L)
//                .agenId(8L)
//                .employeeId(1L)
//                .ciasDTO(ciasDTO)
//                .build();
//
//        // custom multipart for method put
//        MockMultipartHttpServletRequestBuilder builder =
//                MockMvcRequestBuilders.multipart("/customer/service/request");
//        builder.with(new RequestPostProcessor() {
//            @Override
//            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
//                request.setMethod("PUT");
//                return request;
//            }
//        });
//
//        mockMvc.perform(builder
//                .file(file)
//                .accept(MediaType.APPLICATION_JSON)
//                .param("client", objectMapper.writeValueAsString(updateCustomerRequestDTO))
//        ).andExpectAll(
//                status().isNotFound()
//        ).andDo(print());
//
//    }
//}
//
//
//
//
//
//
//
//
//
//
