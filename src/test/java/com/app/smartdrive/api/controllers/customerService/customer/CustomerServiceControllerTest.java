package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.HR.response.EmployeesAreaWorkgroupResponseDto;
import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.CustomerInscAssetsResponseDTO;
import com.app.smartdrive.api.dto.customer.response.ClaimResponseDTO;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.dto.master.response.ArwgRes;
import com.app.smartdrive.api.entities.customer.CustomerInscAssets;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.customer.CustomerClaimService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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

    public CustomerRequestDTO getCustomerRequestDTO(){
        Long[] cuexIds = {7L,8L,9L};

        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = CustomerInscAssetsRequestDTO.builder()
                .ciasPoliceNumber("B 1234 CBD")
                .cuexIds(cuexIds)
                .ciasStartdate(String.valueOf(LocalDateTime.of(2001, Month.OCTOBER, 23, 10, 10, 10)))
                .ciasIntyName("Total Loss Only")
                .ciasCityId(2L)
                .ciasPaidType("CREDIT")
                .ciasIsNewChar('N')
                .ciasYear("2020")
                .ciasCarsId(1L)
                .currentPrice(BigDecimal.valueOf(200_000_000))
                .build();

        CustomerRequestDTO customerRequestDTO = CustomerRequestDTO.builder()
                .customerId(2L)
                .agenId(1L)
                .employeeId(1L)
                .customerInscAssetsRequestDTO(customerInscAssetsRequestDTO)
                .build();

        return customerRequestDTO;
    }
    public UpdateCustomerRequestDTO getUpdateCustomerRequestDTO(Long creqEntityId){
        Long[] cuexIds = {7L,8L,9L};

        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = CustomerInscAssetsRequestDTO.builder()
                .ciasPoliceNumber("B 1234 CBD")
                .cuexIds(cuexIds)
                .ciasStartdate(String.valueOf(LocalDateTime.of(2001, Month.OCTOBER, 23, 10, 10, 10)))
                .ciasIntyName("Total Loss Only")
                .ciasCityId(2L)
                .ciasPaidType("CREDIT")
                .ciasIsNewChar('N')
                .ciasYear("2020")
                .ciasCarsId(1L)
                .currentPrice(BigDecimal.valueOf(200_000_000))
                .build();

        UpdateCustomerRequestDTO customerRequestDTO = UpdateCustomerRequestDTO.builder()
                .creqEntityId(creqEntityId)
                .customerId(2L)
                .agenId(1L)
                .employeeId(1L)
                .customerInscAssetsRequestDTO(customerInscAssetsRequestDTO)
                .build();

        return customerRequestDTO;
    }
    public CreateCustomerRequestByAgenDTO getCustomerRequestByAgenDTO(){
        Long[] cuexIds = {7L,8L,9L};

        CustomerInscAssetsRequestDTO customerInscAssetsRequestDTO = CustomerInscAssetsRequestDTO.builder()
                .ciasPoliceNumber("B 1234 CBD")
                .cuexIds(cuexIds)
                .ciasStartdate(String.valueOf(LocalDateTime.of(2001, Month.OCTOBER, 23, 10, 10, 10)))
                .ciasIntyName("Total Loss Only")
                .ciasCityId(2L)
                .ciasPaidType("CREDIT")
                .ciasIsNewChar('N')
                .ciasYear("2020")
                .ciasCarsId(1L)
                .currentPrice(BigDecimal.valueOf(200_000_000))
                .build();

        CreateCustomerRequestByAgenDTO customerRequestDTO = CreateCustomerRequestByAgenDTO.builder()
                .employeeId(2L)
                .agenId(1L)
                .employeeId(1L)
                .customerInscAssetsRequestDTO(customerInscAssetsRequestDTO)
                .build();

        return customerRequestDTO;
    }

    public ClaimRequestDTO getClaimRequestDTO(Long creqEntityId){
        return ClaimRequestDTO.builder()
                .creqEntityId(creqEntityId)
                .build();
    }
    public ClaimResponseDTO getClaimResponseDTO(Long creqEntity){
        return ClaimResponseDTO.builder()
                .cuclCreqEntityId(creqEntity)
                .cuclEventPrice(BigDecimal.valueOf(100_000_000))
                .cuclSubtotal(BigDecimal.valueOf(150_000_000))
                .cuclEvents(1)
                .cuclCreateDate(LocalDateTime.of(2001, Month.OCTOBER, 23, 12, 12, 0))
                .build();
    }
    public CustomerResponseDTO getCustomerResponseDTO(Long id, String type, String status){
        LocalDateTime ciasStartDate = LocalDateTime.of(2001, Month.OCTOBER, Math.toIntExact(id), 12, 10, 0);

        CustomerInscAssetsResponseDTO customerInscAssetsResponseDTO = CustomerInscAssetsResponseDTO.builder()
                .ciasCreqEntityid(id)
                .ciasCurrentPrice(BigDecimal.valueOf(200_000_000))
                .ciasStartdate(ciasStartDate)
                .ciasEnddate(ciasStartDate.plusYears(1))
                .ciasYear("200" + id)
                .ciasInsurancePrice(BigDecimal.valueOf(200_000_000))
                .ciasPoliceNumber("B 123" + id + " CBD")
                .build();

        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder()
                .creqEntityId(id)
                .creqStatus(EnumCustomer.CreqStatus.valueOf(status))
                .creqType(EnumCustomer.CreqType.valueOf(type))
                .customerInscAssets(customerInscAssetsResponseDTO)
                .build();

        return customerResponseDTO;
    }

    public CustomerRequest getCustomerRequest(Long creqEntityId){
        CustomerInscAssets customerInscAssets = CustomerInscAssets.builder()
                .ciasCreqEntityid(creqEntityId)
                .ciasPoliceNumber("B 123"+creqEntityId + " CBD")
                .build();

        return CustomerRequest.builder()
                .creqEntityId(creqEntityId)
                .customerInscAssets(customerInscAssets)
                .build();
    }
    public EmployeeAreaWorkgroup getEmployeeAreaworkgroup(String arwgCode, Long employeeId){
        AreaWorkGroup areaWorkGroup = new AreaWorkGroup();
        areaWorkGroup.setArwgCode(arwgCode);

        User employeeEntity = new User();
        employeeEntity.setUserEntityId(employeeId);

        Employees employee = new Employees();
        employee.setUser(employeeEntity);

        EmployeeAreaWorkgroup employeeAreaWorkgroup = new EmployeeAreaWorkgroup();
        employeeAreaWorkgroup.setAreaWorkGroup(areaWorkGroup);
        employeeAreaWorkgroup.setEmployees(employee);

        return employeeAreaWorkgroup;
    }


    @Test
    @WithMockUser(authorities = "Customer")
    void getAllCustomersRequest_willSuccess() throws Exception {
        List<CustomerRequest> customerRequestList = List.of(
                getCustomerRequest(1L),
                getCustomerRequest(2L)
        );

        Page<CustomerRequest> pagedCustomerRequest = new PageImpl(customerRequestList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getAllPaging(paging, "ALL", "OPEN")).thenReturn(pagedCustomerRequest);
        when(this.customerRequestService.getAllPaging(paging, "POLIS", "OPEN")).thenReturn(pagedCustomerRequest);

        mockMvc.perform(
                get("/customer/service/request")
                        .param("type", "POLIS")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerRequestList.size()))
                .andExpect(jsonPath("$.content[0].creqEntityId").value(customerRequestList.get(0).getCreqEntityId()))
                .andExpect(jsonPath("$.content[1].creqEntityId").value(customerRequestList.get(1).getCreqEntityId()))
                .andExpect(jsonPath("$.content[0].customerInscAssets.ciasPoliceNumber").value(customerRequestList.get(0).getCustomerInscAssets().getCiasPoliceNumber()))
                .andExpect(jsonPath("$.content[1].customerInscAssets.ciasPoliceNumber").value(customerRequestList.get(1).getCustomerInscAssets().getCiasPoliceNumber()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getAllCustomersRequest_willFailed() throws Exception {
        String typeParam = "SEMUA";

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());


        mockMvc.perform(
                        get("/customer/service/salah/request")
                                .param("type", typeParam)

                )
                .andExpectAll(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getAllUserCustomersRequest_willSuccess() throws Exception {
        User user = new User();
        user.setUserEntityId(23L);

        List<CustomerRequest> customerRequestList = List.of(
                getCustomerRequest(1L),
                getCustomerRequest(2L)
        );

        for (CustomerRequest customerRequest : customerRequestList) {
            customerRequest.setCustomer(user);
        }


        Page<CustomerRequest> pagedResponse = new PageImpl(customerRequestList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingUserCustomerRequest(user.getUserEntityId(), paging, "ALL", "OPEN")).thenReturn(pagedResponse);

        mockMvc.perform(
                get("/customer/service/request/customer")
                        .param("customerId", user.getUserEntityId().toString())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerRequestList.size()))
                .andExpect(jsonPath("$.content[0].creqEntityId").value(customerRequestList.get(0).getCreqEntityId()))
                .andExpect(jsonPath("$.content[1].creqEntityId").value(customerRequestList.get(1).getCreqEntityId()))
                .andExpect(jsonPath("$.content[0].customerInscAssets.ciasPoliceNumber").value(customerRequestList.get(0).getCustomerInscAssets().getCiasPoliceNumber()))
                .andExpect(jsonPath("$.content[1].customerInscAssets.ciasPoliceNumber").value(customerRequestList.get(1).getCustomerInscAssets().getCiasPoliceNumber()))
                .andExpect(jsonPath("$.content[0].customer.userEntityId").value(customerRequestList.get(0).getCustomer().getUserEntityId()))
                .andExpect(jsonPath("$.content[1].customer.userEntityId").value(customerRequestList.get(1).getCustomer().getUserEntityId()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getAllUserCustomersRequest_willFailed() throws Exception{
       Long customerId = 1L;

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingUserCustomerRequest(customerId, paging, "ALL", "OPEN"))
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
        String arwgCode = "BCI-0001";
        Long employeeId = 3L;

        EmployeeAreaWorkgroup employeeAreaworkgroup = getEmployeeAreaworkgroup(arwgCode, employeeId);


        List<CustomerRequest> customerRequestList = List.of(
                getCustomerRequest(1L),
                getCustomerRequest(2L)
        );

        List<String> arwgCodeList = new ArrayList<>();

        for (CustomerRequest customerRequest : customerRequestList) {
            customerRequest.setEmployeeAreaWorkgroup(employeeAreaworkgroup);
            arwgCodeList.add(customerRequest.getEmployeeAreaWorkgroup().getAreaWorkGroup().getArwgCode());
        }

        Page<CustomerRequest> pagedResponse = new PageImpl(customerRequestList);

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingAgenCustomerRequest(employeeId, arwgCode, paging, "ALL", "OPEN")).thenReturn(pagedResponse);

        mockMvc.perform(
                        get("/customer/service/request/agen")
                                .param("employeeId", employeeId.toString())
                                .param("arwgCode", arwgCode)

                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerRequestList.size()))
                .andExpect(jsonPath("$.content[0].creqEntityId").value(customerRequestList.get(0).getCreqEntityId()))
                .andExpect(jsonPath("$.content[1].creqEntityId").value(customerRequestList.get(1).getCreqEntityId()))
                .andExpect(jsonPath("$.content[0].customerInscAssets.ciasPoliceNumber").value(customerRequestList.get(0).getCustomerInscAssets().getCiasPoliceNumber()))
                .andExpect(jsonPath("$.content[1].customerInscAssets.ciasPoliceNumber").value(customerRequestList.get(1).getCustomerInscAssets().getCiasPoliceNumber()))
                .andExpect(jsonPath("$.content[0].employeeAreaWorkgroup.employees.user.userEntityId").value(customerRequestList.get(0).getEmployeeAreaWorkgroup().getEmployees().getUser().getUserEntityId()))
                .andExpect(jsonPath("$.content[1].employeeAreaWorkgroup.employees.user.userEntityId").value(customerRequestList.get(1).getEmployeeAreaWorkgroup().getEmployees().getUser().getUserEntityId()))
                .andExpect(jsonPath("$.content[*].employeeAreaWorkgroup.areaWorkGroup.arwgCode").value(arwgCodeList))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Employee")
    void getAllAgenCustomersRequest_willFailed() throws Exception {
        String arwgCode = "asal-asal";
        Long employeeId = 23L;

        Pageable paging = PageRequest.of(0, 3, Sort.by("creqEntityId").ascending());

        when(this.customerRequestService.getPagingAgenCustomerRequest(employeeId, arwgCode, paging, "ALL", "OPEN"))
                .thenThrow(new EntityNotFoundException("Agen with id : " + employeeId + " is not found"));

        mockMvc.perform(
                        get("/customer/service/request/agen")
                                .param("employeeId", employeeId.toString())
                                .param("arwgCode", arwgCode)

                ).andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Agen with id : " + employeeId+ " is not found", result.getResolvedException().getMessage()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getById_willSuccess() throws Exception {
        Long creqEntityId = 1L;

        CustomerRequest customerRequest = getCustomerRequest(creqEntityId);

        when(this.customerRequestService.getById(creqEntityId)).thenReturn(customerRequest);

        mockMvc.perform(
                get("/customer/service/request/search")
                        .param("creqEntityId", creqEntityId.toString())

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CustomerResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(creqEntityId, response.getCreqEntityId());
            assertEquals(customerRequest.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
        });
    }

    @Test
    @WithMockUser(authorities = {"Customer", "Employee"})
    void getById_willFailed() throws Exception {

        Long creqEntityId = 2L;

        when(this.customerRequestService.getById(creqEntityId))
                .thenThrow(new EntityNotFoundException("Customer Request with id " + creqEntityId + " is not found"));

        mockMvc.perform(
                get("/customer/service/request/search")
                        .param("creqEntityId", creqEntityId.toString())

        ).andExpect(
                status().isNotFound()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException)
        ).andExpect(result -> assertEquals("Customer Request with id " + creqEntityId + " is not found", result.getResolvedException().getMessage())
        ).andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Potential Customer")
    void create_willSuccess() throws Exception {
        Long creqEntityId = 1L;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};

        CustomerRequestDTO customerRequestDTO = getCustomerRequestDTO();

        CustomerRequest customerRequest = getCustomerRequest(creqEntityId);

        when(this.customerRequestService.create(customerRequestDTO, multipartFiles))
                .thenReturn(customerRequest);

        mockMvc.perform(multipart("/customer/service/request")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(customerRequestDTO))
        ).andExpect(status().isCreated()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerRequest.getCreqEntityId(), response.getCreqEntityId());
            assertEquals(customerRequest.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
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

        CustomerRequestDTO customerRequestDTO = getCustomerRequestDTO();

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
        Long creqEntityId = 10L;

        String arwgCode = "BCI-0001";
        Long employeeId = 3L;

        EmployeeAreaWorkgroup employeeAreaworkgroup = getEmployeeAreaworkgroup(arwgCode, employeeId);

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};

        CreateCustomerRequestByAgenDTO createCustomerRequestByAgenDTO = getCustomerRequestByAgenDTO();

        CustomerRequest customerRequest = getCustomerRequest(creqEntityId);
        customerRequest.setEmployeeAreaWorkgroup(employeeAreaworkgroup);

        when(this.customerRequestService.createByAgen(createCustomerRequestByAgenDTO, multipartFiles))
                .thenReturn(customerRequest);

        mockMvc.perform(multipart("/customer/service/request/agen")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(createCustomerRequestByAgenDTO))
        ).andExpect(status().isCreated()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerRequest.getCreqEntityId(), response.getCreqEntityId());
            assertEquals(customerRequest.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
            assertEquals(customerRequest.getEmployeeAreaWorkgroup().getEmployees().getUser().getUserEntityId(), response.getEmployeeAreaWorkgroup().getEmployees().getUser().getUserEntityId());
            assertEquals(customerRequest.getEmployeeAreaWorkgroup().getAreaWorkGroup().getArwgCode(), response.getEmployeeAreaWorkgroup().getAreaWorkGroup().getArwgCode());
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

        CreateCustomerRequestByAgenDTO createCustomerRequestByAgenDTO = getCustomerRequestByAgenDTO();

        when(this.customerRequestService.createByAgen(createCustomerRequestByAgenDTO, multipartFiles))
                .thenThrow(new Exception("CustomerRequest with police number " + createCustomerRequestByAgenDTO.getCustomerInscAssetsRequestDTO().getCiasPoliceNumber() + " is already exist"));

        mockMvc.perform(multipart("/customer/service/request/agen")
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(createCustomerRequestByAgenDTO))
        ).andExpect(status().isInternalServerError()
        ).andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception)
        ).andExpect(result -> assertEquals(
                "CustomerRequest with police number " + createCustomerRequestByAgenDTO.getCustomerInscAssetsRequestDTO().getCiasPoliceNumber() + " is already exist",
                result.getResolvedException().getMessage())
        ).andDo(print());
    }

    @Test
    @WithMockUser(authorities = "Customer")
    void update_willSuccess() throws Exception {
        Long creqEntityId = 1L;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};

        UpdateCustomerRequestDTO customerRequestDTO = getUpdateCustomerRequestDTO(creqEntityId);

        CustomerRequest customerRequest = getCustomerRequest(creqEntityId);

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
                .thenReturn(customerRequest);

        mockMvc.perform(builder
                .file(file)
                .param("client", this.objectMapper.writeValueAsString(customerRequestDTO))
        ).andExpect(status().isOk()
        ).andDo(result -> {
            CustomerResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);
            assertEquals(customerRequest.getCreqEntityId(), response.getCreqEntityId());
            assertEquals(customerRequest.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
        });
    }

    @Test
    @WithMockUser(authorities = "Customer")
    void update_willFailed() throws Exception {
        Long creqEntityId = 1L;

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MultipartFile[] multipartFiles = {file};



        UpdateCustomerRequestDTO updateCustomerRequestDTO = getUpdateCustomerRequestDTO(creqEntityId);

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
        Long cuclCreqEntityId = 1L;
        String type = "CLAIM";
        String status = "OPEN";

        ClaimRequestDTO claimRequestDTO = getClaimRequestDTO(cuclCreqEntityId);

        CustomerResponseDTO customerResponseDTO = getCustomerResponseDTO(cuclCreqEntityId, type, status);

        when(this.customerClaimService.claimPolis(claimRequestDTO))
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
            assertEquals(cuclCreqEntityId, response.getCreqEntityId());
            assertEquals(type, response.getCreqType().toString());
            assertEquals(status, response.getCreqStatus().toString());
            assertEquals(customerResponseDTO.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
        });


    }

    @Test
    @WithMockUser(authorities = "Customer")
    void updateCustomerClaim_willFailed() throws Exception {
        Long cuclCreqEntityId = 1L;

        ClaimRequestDTO claimRequestDTO = getClaimRequestDTO(cuclCreqEntityId);


        when(this.customerClaimService.claimPolis(claimRequestDTO))
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
        Long cuclCreqEntityId = 1L;

        ClaimResponseDTO customerClaim = getClaimResponseDTO(cuclCreqEntityId);

        when(this.customerClaimService.getCustomerClaimById(customerClaim.getCuclCreqEntityId()))
                .thenReturn(customerClaim);

        mockMvc.perform(get("/customer/service/request/claim")
                .param("cuclCreqEntityId", cuclCreqEntityId.toString())
        ).andExpectAll(status().isOk()
        ).andDo(result -> {
            ClaimResponseDTO response = this.objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

           assertNotNull(response);
           assertEquals(customerClaim.getCuclCreqEntityId(), response.getCuclCreqEntityId());
           assertEquals(customerClaim.getCuclEvents(), response.getCuclEvents());
           assertEquals(customerClaim.getCuclCreateDate(), response.getCuclCreateDate());
           assertEquals(customerClaim.getCuclSubtotal(), response.getCuclSubtotal());
           assertEquals(customerClaim.getCuclEventPrice(), response.getCuclEventPrice());
        });


    }

    @Test
    @WithMockUser(authorities = "Customer")
    void getCustomerClaimById_willFailed() throws Exception {
        Long creqEntityId = 1L;

        ClaimResponseDTO customerClaim = getClaimResponseDTO(creqEntityId);

        when(this.customerClaimService.getCustomerClaimById(customerClaim.getCuclCreqEntityId()))
                .thenThrow(new EntityNotFoundException("Customer Claim with id " + customerClaim.getCuclCreqEntityId() + " is not found"));

        mockMvc.perform(get("/customer/service/request/claim")
                .param("cuclCreqEntityId", creqEntityId.toString())
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
        Long creqEntityId = 1L;
        String type = "CLOSE";
        String status = "CLOSED";

        CloseRequestDTO closeRequestDTO = CloseRequestDTO.builder()
                .creqEntityId(creqEntityId)
                .cuclReason("G ada duit")
                .build();

        CustomerResponseDTO customerResponseDTO = getCustomerResponseDTO(creqEntityId, type, status);

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
            assertEquals(creqEntityId, response.getCreqEntityId());
            assertEquals(type, response.getCreqType().toString());
            assertEquals(status, response.getCreqStatus().toString());
            assertEquals(customerResponseDTO.getCustomerInscAssets().getCiasPoliceNumber(), response.getCustomerInscAssets().getCiasPoliceNumber());
        });

    }

    @Test
    @WithMockUser(authorities = "Customer")
    void requestClosePolis_willFailed() throws Exception{

        Long creqEntityId = 1L;

        CloseRequestDTO closeRequestDTO = CloseRequestDTO.builder()
                .creqEntityId(creqEntityId)
                .cuclReason("G ada duit")
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
