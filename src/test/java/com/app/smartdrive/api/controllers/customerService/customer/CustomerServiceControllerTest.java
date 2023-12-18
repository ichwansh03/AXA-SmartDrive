package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.entities.customer.CustomerClaim;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.customer.impl.CustomerRequestServiceImpl;
import com.app.smartdrive.api.services.master.CarsService;
import com.app.smartdrive.api.services.master.CityService;
import com.app.smartdrive.api.services.master.IntyService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceControllerTest {

    @MockBean
    private CustomerRequestService customerRequestService;

    @MockBean
    private CustomerClaimService customerClaimService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(authorities = "Customer")
    void getAllCustomersRequest_willSuccess() throws Exception {
        List<CustomerResponseDTO> customerResponseDTOList = List.of(new CustomerResponseDTO(), new CustomerResponseDTO());
        Page<CustomerResponseDTO> pagedResponse = new PageImpl(customerResponseDTOList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getAllPaging(paging, "ALL", "OPEN")).thenReturn(pagedResponse);

        mockMvc.perform(
                get("/customer/service/request"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerResponseDTOList.size()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getAllCustomersRequest_willFailed() throws Exception {
        List<CustomerResponseDTO> customerResponseDTOList = List.of(new CustomerResponseDTO(), new CustomerResponseDTO());
        Page<CustomerResponseDTO> pagedResponse = new PageImpl(customerResponseDTOList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getAllPaging(paging, "ALL", "OPEN")).thenReturn(pagedResponse);

        mockMvc.perform(
                        get("/customer/service/request")
                                .param("type", "Semua")

                )
                .andExpectAll(status().isOk())
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getAllUserCustomersRequest_willSuccess() throws Exception {
        User user = new User();
        user.setUserEntityId(1L);

        List<CustomerResponseDTO> customerResponseDTOList = List.of(new CustomerResponseDTO(), new CustomerResponseDTO());
        Page<CustomerResponseDTO> pagedResponse = new PageImpl(customerResponseDTOList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingUserCustomerRequests(user.getUserEntityId(), paging, "ALL", "OPEN")).thenReturn(pagedResponse);

        mockMvc.perform(
                get("/customer/service/request/customer")
                        .param("customerId", user.getUserEntityId().toString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerResponseDTOList.size()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getAllUserCustomersRequest_willFailed() throws Exception{
       Long customerId = 1L;

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingUserCustomerRequests(customerId, paging, "ALL", "OPEN"))
                .thenThrow(new EntityNotFoundException("User with id "+ customerId + " is not found"));

        mockMvc.perform(
                        get("/customer/service/request/customer")
                                .param("customerId", customerId.toString())
        ).andExpect(status().isNotFound()
        ).andExpect(result -> assertTrue((result.getResolvedException() instanceof EntityNotFoundException))
        ).andExpect(result -> assertEquals("User with id "+ customerId + " is not found", result.getResolvedException().getMessage())
        ).andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Employee")
    void getAllAgenCustomersRequest_willSuccess() throws Exception {
        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();
        areaWorkGroup.setArwgCode("BCI-0001");

        EmployeeAreaWorkgroup eawag = EmployeeAreaWorkgroup.builder()
                .eawgId(1L)
                .eawgEntityid(2L)
                .areaWorkGroup(areaWorkGroup)
                .build();

        List<CustomerResponseDTO> customerResponseDTOList = List.of(new CustomerResponseDTO(), new CustomerResponseDTO());
        Page<CustomerResponseDTO> pagedResponse = new PageImpl(customerResponseDTOList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingAgenCustomerRequest(eawag.getEawgEntityid(), areaWorkGroup.getArwgCode(), paging, "ALL", "OPEN")).thenReturn(pagedResponse);

        mockMvc.perform(
                        get("/customer/service/request/agen")
                                .param("employeeId", String.valueOf(2))
                                .param("arwgCode", "BCI-0001")

                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerResponseDTOList.size()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Employee")
    void getAllAgenCustomersRequest_willFailed() throws Exception {
        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();
        areaWorkGroup.setArwgCode("BCI-0001");

        EmployeeAreaWorkgroup eawag = EmployeeAreaWorkgroup.builder()
                .eawgId(1L)
                .eawgEntityid(2L)
                .areaWorkGroup(areaWorkGroup)
                .build();

        List<CustomerResponseDTO> customerResponseDTOList = List.of(new CustomerResponseDTO(), new CustomerResponseDTO());
        Page<CustomerResponseDTO> pagedResponse = new PageImpl(customerResponseDTOList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingAgenCustomerRequest(eawag.getEawgEntityid(), areaWorkGroup.getArwgCode(), paging, "ALL", "OPEN"))
                .thenThrow(new EntityNotFoundException("Agen with id : " + eawag.getEawgEntityid() + " is not found"));

        mockMvc.perform(
                        get("/customer/service/request/agen")
                                .param("employeeId", String.valueOf(2))
                                .param("arwgCode", "BCI-0001")

                ).andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Agen with id : " + eawag.getEawgEntityid() + " is not found", result.getResolvedException().getMessage()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getById_willSuccess() throws Exception {

        CustomerRequest customerRequest = CustomerRequest.builder()
                .creqEntityId(1L)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(customerRequest.getCreqEntityId())
                .build();

        when(this.customerRequestService.getCustomerRequestById(customerRequest.getCreqEntityId())).thenReturn(customerResponseDTO);

        mockMvc.perform(
                get("/customer/service/request/search")
                        .param("creqEntityId", String.valueOf(1L))

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CustomerResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerResponseDTO, response);
        });
    }

    @Test
    @WithMockUser(authorities = {"Customer", "Employee"})
    void getById_willFailed() throws Exception {

        Long creqEntityId = 2L;

        CustomerRequest customerRequest = CustomerRequest.builder()
                .creqEntityId(1L)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(customerRequest.getCreqEntityId())
                .build();



        when(this.customerRequestService.getCustomerRequestById(creqEntityId))
                .thenThrow(new EntityNotFoundException("Customer Request with id " + creqEntityId + " is not found"));

        mockMvc.perform(
                get("/customer/service/request/search")
                        .param("creqEntityId", String.valueOf(2L))

        ).andExpect(
                status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
        ).andExpect(result -> assertEquals("Customer Request with id " + creqEntityId + " is not found", result.getResolvedException().getMessage())
        ).andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Potential Customer")
    void create_willSuccess() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};



        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
                .employeeId(1L)
                .agenId(1L)
                .customerId(2L)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(customerRequestDTO.getCustomerId())
                .build();

        when(this.customerRequestService.create(customerRequestDTO, multipartFiles))
                .thenReturn(customerResponseDTO);

        mockMvc.perform(multipart("/customer/service/request")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(customerRequestDTO))
        ).andExpect(status().isCreated()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerResponseDTO, response);
        });
    }

    @Test
    @WithMockUser(authorities = "Potential Customer")
    void create_willFailed() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};



        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
                .employeeId(1L)
                .agenId(1L)
                .customerId(2L)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(customerRequestDTO.getCustomerId())
                .build();

        when(this.customerRequestService.create(customerRequestDTO, multipartFiles))
                .thenThrow(new EntityNotFoundException("User with id " + customerRequestDTO.getCustomerId() + " is not found"));

        mockMvc.perform(multipart("/customer/service/request")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(customerRequestDTO))
        ).andExpect(status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
        ).andExpect(result -> assertEquals("User with id " + customerRequestDTO.getCustomerId() + " is not found", result.getResolvedException().getMessage())
        ).andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Employee")
    void createByAgen_willSuccess() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};



        CreateCustomerRequestByAgenDTO createCustomerRequestByAgenDTO = CreateCustomerRequestByAgenDTO.builder()
                .employeeId(1L)
                .agenId(1L)
                .accessGrantUser(true)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(1L)
                .build();




        when(this.customerRequestService.createByAgen(createCustomerRequestByAgenDTO, multipartFiles))
                .thenReturn(customerResponseDTO);

        mockMvc.perform(multipart("/customer/service/request/agen")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(createCustomerRequestByAgenDTO))
        ).andExpect(status().isCreated()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerResponseDTO, response);
        });
    }

    @Test
    @WithMockUser(authorities = "Employee")
    void createByAgen_willFailed() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};


        CiasDTO ciasDTO = CiasDTO.builder()
                .ciasPoliceNumber("B 1234 CBD")
                .build();

        CreateCustomerRequestByAgenDTO createCustomerRequestByAgenDTO = CreateCustomerRequestByAgenDTO.builder()
                .employeeId(1L)
                .agenId(1L)
                .accessGrantUser(true)
                .ciasDTO(ciasDTO)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(1L)
                .build();




        when(this.customerRequestService.createByAgen(createCustomerRequestByAgenDTO, multipartFiles))
                .thenThrow(new Exception("CustomerRequest with police number " + createCustomerRequestByAgenDTO.getCiasDTO().getCiasPoliceNumber() + " is already exist"));

        mockMvc.perform(multipart("/customer/service/request/agen")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(createCustomerRequestByAgenDTO))
        ).andExpect(status().isInternalServerError()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception)
        ).andExpect(result -> assertEquals(
                "CustomerRequest with police number " + createCustomerRequestByAgenDTO.getCiasDTO().getCiasPoliceNumber() + " is already exist",
                result.getResolvedException().getMessage())
        ).andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Customer")
    void update_willSuccess() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};



        UpdateCustomerRequestDTO customerRequestDTO = UpdateCustomerRequestDTO.builder()
                .creqEntityId(1L)
                .employeeId(1L)
                .agenId(1L)
                .customerId(2L)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(customerRequestDTO.getCustomerId())
                .build();

        // custom http method for multipart
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/customer/service/request");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        when(this.customerRequestService.updateCustomerRequest(customerRequestDTO, multipartFiles))
                .thenReturn(customerResponseDTO);

        mockMvc.perform(builder
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(customerRequestDTO))
        ).andExpect(status().isOk()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerResponseDTO, response);
        });
    }

    @Test
    @WithMockUser(authorities = "Customer")
    void update_willFailed() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};



        UpdateCustomerRequestDTO updateCustomerRequestDTO = UpdateCustomerRequestDTO.builder()
                .creqEntityId(1L)
                .employeeId(1L)
                .agenId(1L)
                .customerId(2L)
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(updateCustomerRequestDTO.getCustomerId())
                .build();

        // custom http method for multipart
        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/customer/service/request");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        when(this.customerRequestService.updateCustomerRequest(updateCustomerRequestDTO, multipartFiles))
                .thenThrow(new EntityNotFoundException("User with id " + updateCustomerRequestDTO.getCustomerId() +" is not found"));

        mockMvc.perform(builder
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(updateCustomerRequestDTO))
        ).andExpect(status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
        ).andExpect(result -> assertEquals(
                "User with id " + updateCustomerRequestDTO.getCustomerId() + " is not found",
                result.getResolvedException().getMessage())
        ).andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void updateCustomerClaim_willSuccess() throws Exception {
        ClaimRequestDTO claimRequestDTO = ClaimRequestDTO.builder()
                .creqEntityId(1L)
                .cuclSubtotal(new BigDecimal(100000))
                .cuclEventPrice(new BigDecimal(3000))
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(claimRequestDTO.getCreqEntityId())
                .build();

        when(this.customerClaimService.updateCustomerClaim(claimRequestDTO))
                .thenReturn(customerResponseDTO);

        mockMvc.perform(put("/customer/service/request/claim")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(claimRequestDTO))
        ).andExpectAll(status().isOk()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerResponseDTO, response);
        });


    }

    @Test
    @WithMockUser(authorities = "Customer")
    void updateCustomerClaim_willFailed() throws Exception {
        ClaimRequestDTO claimRequestDTO = ClaimRequestDTO.builder()
                .creqEntityId(1L)
                .cuclSubtotal(new BigDecimal(100000))
                .cuclEventPrice(new BigDecimal(3000))
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(claimRequestDTO.getCreqEntityId())
                .build();

        when(this.customerClaimService.updateCustomerClaim(claimRequestDTO))
                .thenThrow(new EntityNotFoundException("Customer Request with id " + claimRequestDTO.getCreqEntityId() + " is not found"));

        mockMvc.perform(put("/customer/service/request/claim")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(claimRequestDTO))
        ).andExpectAll(status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
        ).andExpect(result -> assertEquals(
                "Customer Request with id " + claimRequestDTO.getCreqEntityId() + " is not found",
                result.getResolvedException().getMessage()
                )
        ).andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getCustomerClaimById_willSuccess() throws Exception {

        ClaimResponseDTO customerClaim = ClaimResponseDTO.builder()
                .cuclCreqEntityId(1L)
                .cuclEventPrice(new BigDecimal(10000))
                .cuclSubtotal(new BigDecimal(2000))
                .build();

        when(this.customerClaimService.getCustomerClaimById(customerClaim.getCuclCreqEntityId()))
                .thenReturn(customerClaim);

        mockMvc.perform(get("/customer/service/request/claim")
                .param("cuclCreqEntityId", String.valueOf(1L))
        ).andExpectAll(status().isOk()
        ).andDo(result -> {
            ClaimResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

           assertNotNull(response);
           assertEquals(customerClaim, response);
        });


    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getCustomerClaimById_willFailed() throws Exception {

        ClaimResponseDTO customerClaim = ClaimResponseDTO.builder()
                .cuclCreqEntityId(1L)
                .cuclEventPrice(new BigDecimal(10000))
                .cuclSubtotal(new BigDecimal(2000))
                .build();

        when(this.customerClaimService.getCustomerClaimById(customerClaim.getCuclCreqEntityId()))
                .thenThrow(new EntityNotFoundException("Customer Claim with id " + customerClaim.getCuclCreqEntityId() + " is not found"));

        mockMvc.perform(get("/customer/service/request/claim")
                .param("cuclCreqEntityId", String.valueOf(1L))
        ).andExpect(status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
        ).andExpect(result -> assertEquals(
                "Customer Claim with id " + customerClaim.getCuclCreqEntityId() + " is not found",
                result.getResolvedException().getMessage())
        ).andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void requestClosePolis_willSuccess() throws Exception{

        CloseRequestDTO closeRequestDTO = CloseRequestDTO.builder()
                .creqEntityId(1L)
                .cuclReason("G ada duit")
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(closeRequestDTO.getCreqEntityId())
                .build();

        when(this.customerClaimService.closePolis(closeRequestDTO))
                .thenReturn(customerResponseDTO);

        mockMvc.perform(put("/customer/service/request/close")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(closeRequestDTO))
        ).andExpectAll(status().isOk()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerResponseDTO, response);
        });

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void requestClosePolis_willFailed() throws Exception{

        CloseRequestDTO closeRequestDTO = CloseRequestDTO.builder()
                .creqEntityId(1L)
                .cuclReason("G ada duit")
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(closeRequestDTO.getCreqEntityId())
                .build();

        when(this.customerClaimService.closePolis(closeRequestDTO))
                .thenThrow(new EntityNotFoundException("Customer Request with id " + closeRequestDTO.getCreqEntityId() + " is not found"));

        mockMvc.perform(put("/customer/service/request/close")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(closeRequestDTO))
        ).andExpect(status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof  EntityNotFoundException)
        ).andExpect(result -> assertEquals(
                "Customer Request with id " + closeRequestDTO.getCreqEntityId() + " is not found",
                result.getResolvedException().getMessage())
        ).andDo(print());

    }
}
