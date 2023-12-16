package com.app.smartdrive.api.controllers.customerService.customer;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.customer.response.CustomerResponseDTO;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.EmployeeAreaWorkgroup;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.repositories.customer.CustomerRequestRepository;
import com.app.smartdrive.api.services.HR.EmployeeAreaWorkgroupService;
import com.app.smartdrive.api.services.customer.CustomerRequestService;
import com.app.smartdrive.api.services.customer.impl.CustomerRequestServiceImpl;
import com.app.smartdrive.api.services.master.CarsService;
import com.app.smartdrive.api.services.master.CityService;
import com.app.smartdrive.api.services.master.IntyService;
import com.app.smartdrive.api.services.users.BusinessEntityService;
import com.app.smartdrive.api.services.users.UserService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
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


}
