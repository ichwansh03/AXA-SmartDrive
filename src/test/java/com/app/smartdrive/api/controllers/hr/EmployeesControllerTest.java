package com.app.smartdrive.api.controllers.hr;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.Exceptions.EmployeesNotFoundException;
import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.config.JwtAuthenticationFilter;
import com.app.smartdrive.api.controllers.auth.AuthenticationController;
import com.app.smartdrive.api.controllers.users.RequestBuilder;
import com.app.smartdrive.api.controllers.users.UserController;
import com.app.smartdrive.api.dto.user.response.UserDto;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.services.auth.AuthenticationService;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.refreshToken.RefreshTokenService;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.smartdrive.api.controllers.HR.EmployeesController;
import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.dto.user.request.UserAddressRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.EnumClassHR;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(EmployeesController.class)
@ImportAutoConfiguration(classes = {SecurityConfig.class})
public class EmployeesControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @MockBean
    private ClaimAssetService claimAssetService;

    @MockBean
    JwtService jwtService;
    @MockBean
    EmployeesService employeesService;

    

    EmployeesRequestDto createEmployeesReq(){
        // Mock input

        EmployeesRequestDto requestDto = new EmployeesRequestDto();
        requestDto.setEmpName("test");
        requestDto.setEmail("test@gmail.com");
        requestDto.setGrantAccessUser(true);

        UserPhoneRequestDto phoneDto = new UserPhoneRequestDto();
        phoneDto.setUsphPhoneNumber("11111");
        requestDto.setEmpPhone(phoneDto);

        UserAddressRequestDto addressDto = new UserAddressRequestDto();
        addressDto.setUsdrAddress1("test11");
        addressDto.setUsdrAddress2("test22");
        addressDto.setCityId(1L);
        requestDto.setEmpAddress(addressDto);

        requestDto.setEmpJoinDate("2023-01-01T00:00:00");
        requestDto.setEmpGraduate(EnumClassHR.emp_graduate.S1);
        requestDto.setEmpSalary(new BigDecimal(2100000));
        requestDto.setEmpAccountNumber("18561");
        requestDto.setJobType("FAJ");

        return requestDto;
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    public void createEmployees_willSuccess() throws Exception {
        EmployeesRequestDto requestDto = createEmployeesReq();

         Employees mockedResponse = new Employees();
         TransactionMapper.mapDtoToEntity(requestDto, mockedResponse);
        when(employeesService.createEmployee(requestDto)).thenReturn(TransactionMapper.mapEntityToDto(mockedResponse, EmployeesResponseDto.class));

        // Perform the request and validate the response
        mockMvc.perform(post("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                .with(csrf())
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(result -> jsonPath("$.empName").value(requestDto.getEmpName()))
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void shouldDeleteEmployees() throws Exception {

        EmployeesRequestDto requestDto = createEmployeesReq();

        Employees mockedResponse = new Employees();
        TransactionMapper.mapDtoToEntity(requestDto, mockedResponse);
        mockedResponse.setEmpEntityid(1L);
        long id = mockedResponse.getEmpEntityid();
        doNothing().when(employeesService).deleteEmployeesById(id);

        mockMvc.perform(delete("/employees/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    EmployeesRequestDto updateEmployeesDto(){
        EmployeesRequestDto updateDto = new EmployeesRequestDto();
        updateDto.setEmpName("test2");
        updateDto.setEmail("test2@gmail.com");
        updateDto.setGrantAccessUser(true);

        UserPhoneRequestDto phoneDto = new UserPhoneRequestDto();
        phoneDto.setUsphPhoneNumber("22222");
        updateDto.setEmpPhone(phoneDto);

        UserAddressRequestDto addressDto = new UserAddressRequestDto();
        addressDto.setUsdrAddress1("test111");
        addressDto.setUsdrAddress2("test222");
        addressDto.setCityId(1L);
        updateDto.setEmpAddress(addressDto);

        updateDto.setEmpJoinDate("2023-01-01T00:00:00");
        updateDto.setEmpGraduate(EnumClassHR.emp_graduate.S1);
        updateDto.setEmpSalary(new BigDecimal(2000000));
        updateDto.setEmpAccountNumber("22222");
        updateDto.setJobType("FAJ");

        return updateDto;
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void UpdateEmployees_willSuccsess() throws Exception {

        EmployeesRequestDto requestDto = createEmployeesReq();

        Employees mockedResponse = new Employees();
        TransactionMapper.mapDtoToEntity(requestDto, mockedResponse);
        mockedResponse.setEmpEntityid(1L);
        long id = mockedResponse.getEmpEntityid();

        //updateDTO
        EmployeesRequestDto updateDto = updateEmployeesDto();
        TransactionMapper.mapDtoToEntity(updateDto, mockedResponse);

        when(employeesService.updateEmployee(id,updateDto)).thenReturn(TransactionMapper.mapEntityToDto(mockedResponse, EmployeesResponseDto.class));


        mockMvc.perform(put("/employees/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void UpdateEmployees_willFail() throws Exception {
        EmployeesRequestDto requestDto = createEmployeesReq();

        Employees mockedResponse = new Employees();
        TransactionMapper.mapDtoToEntity(requestDto, mockedResponse);
        mockedResponse.setEmpEntityid(1L);
        long id = mockedResponse.getEmpEntityid();

        EmployeesRequestDto updateDto = updateEmployeesDto();
        TransactionMapper.mapDtoToEntity(updateDto, mockedResponse);

        when(employeesService.updateEmployee(id,updateDto)).thenThrow(new EmployeesNotFoundException("Employee with " + mockedResponse.getEmpName() + " update failed"));

        mockMvc.perform(put("/employees/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmployeesNotFoundException))
                .andDo(print());
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void getAllEmployees_willSuccess() throws Exception {
        List<Employees> listEmployees = List.of(new Employees(), new Employees());

        List<EmployeesResponseDto> employeesResponseDtos = TransactionMapper.mapEntityListToDtoList(listEmployees, EmployeesResponseDto.class);

        Page<EmployeesResponseDto> mockEmployeesPage = new PageImpl<>(employeesResponseDtos);

        when(employeesService.getAll(any(Pageable.class))).thenReturn(mockEmployeesPage);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @WithMockUser(authorities = {"Admin"})
    void getAllEmployees_willFail() throws Exception {
        List<Employees> listEmployees = List.of(new Employees(), new Employees());

        List<EmployeesResponseDto> employeesResponseDtos = TransactionMapper.mapEntityListToDtoList(listEmployees, EmployeesResponseDto.class);


        when(employeesService.getAll(any(Pageable.class))).thenThrow(new EntityNotFoundException("Employees Not Found"));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(EntityNotFoundException.class, result.getResolvedException()))
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void getEmployeesByNameOrGraduate_willSuccess() throws Exception {
        List<Employees> listEmployees = List.of(new Employees(), new Employees());

        List<EmployeesResponseDto> employeesResponseDtos = TransactionMapper.mapEntityListToDtoList(listEmployees, EmployeesResponseDto.class);

        Page<EmployeesResponseDto> mockEmployeesPage = new PageImpl<>(employeesResponseDtos);

        when(employeesService.searchEmployees(anyString(), anyInt(), anyInt())).thenReturn(mockEmployeesPage);

        mockMvc.perform(get("/employees/search")
                        .param("value", "Value")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @WithMockUser(authorities = {"Admin"})
    void getEmployeesByNameOrGraduate_willFail() throws Exception {
        List<Employees> listEmployees = List.of(new Employees(), new Employees());
        Page<Employees> mockEmployeesPage = new PageImpl<>(listEmployees);

        when(employeesService.searchEmployees(anyString(), anyInt(), anyInt()))
                .thenThrow(new EntityNotFoundException("Not Found"));

        mockMvc.perform(get("/employees/search")
                        .param("value", "someValue")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
