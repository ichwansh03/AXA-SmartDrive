package com.app.smartdrive.api.controllers.hr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.app.smartdrive.api.dto.user.response.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(classes = {SecurityConfig.class})
public class EmployeesControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @InjectMocks
    private EmployeesController employeesController;

    @Autowired
    EmployeesRepository employeesRepository;

    @MockBean
    EmployeesService employeesService;

    

    EmployeesRequestDto createEmployees(){
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
    public void testCreateEmployee() throws Exception {

        EmployeesRequestDto requestDto = createEmployees();

         Employees mockedResponse = new Employees();
         TransactionMapper.mapDtoToEntity(requestDto, mockedResponse);
         when(employeesService.createEmployee(requestDto)).thenReturn(mockedResponse);

        // Perform the request and validate the response
        mockMvc.perform(post("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                .with(csrf())
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andDo(result -> {
                    EmployeesResponseDto response = this.objectMapper.readValue(result.getResponse().getContentAsString(),new TypeReference<>() {
                    });
                    assertNotNull(response);
                    assertEquals(requestDto, response);
                });
    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void shouldDeleteEmployees() throws Exception {

        EmployeesRequestDto requestDto = createEmployees();

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
                .andExpect(status().isNoContent())
                .andDo(print());

    }

    @Test
    @WithMockUser(authorities = {"Admin"})
    void shouldUpdateEmployees() throws Exception {

        EmployeesRequestDto requestDto = createEmployees();

        Employees mockedResponse = new Employees();
        TransactionMapper.mapDtoToEntity(requestDto, mockedResponse);
        mockedResponse.setEmpEntityid(1L);
        long id = mockedResponse.getEmpEntityid();

        //updateDTO
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

        when(employeesService.editEmployee(id,updateDto)).thenReturn(mockedResponse);

        mockMvc.perform(put("/employees/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("users").authorities(List.of(new SimpleGrantedAuthority("Admin"))))
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    void shouldUpdateCategory() throws Exception {
//        long id = 1L;
//
//        CategoryDto category = new CategoryDto(id, "Musics", "K-Pop, J-Pop, West");
//        CategoryDto categoryUpdate = new CategoryDto(id, "Musics", "Bollywood");
//
//        when(categoryService.findById(id)).thenReturn(Optional.of(category));
//        when(categoryService.createCategory(any(Category.class))).thenReturn(categoryUpdate);
//
//        mockMvc.perform(put("/api/category/{id}", id).contentType(MediaType.APPLICATION_JSON)
//                        .with(user("kangdian").roles("ADMIN"))
//                        .with(csrf())
//                        .content(objectMapper.writeValueAsString(categoryUpdate)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.categoryName").value(categoryUpdate.getCategoryName()))
//                .andExpect(jsonPath("$.description").value(categoryUpdate.getDescription()))
//                .andDo(print());
//    }







    // @Test
    // @WithMockUser(roles = {"ADMIN"})
    // void shouldDeleteCategory() throws Exception {
    //     long id = 1L;

    //     CategoryDto categoryDelete = new CategoryDto(id, "Musics", "Bollywood");

    //     doNothing().when(categoryService).deleteCategory(id);

    //     mockMvc.perform(delete("/api/category/{id}", id).contentType(MediaType.APPLICATION_JSON)
    //                     .with(user("kangdian").roles("ADMIN"))
    //                     .with(csrf())
    //                     .content(objectMapper.writeValueAsString(categoryDelete)))
    //             .andExpect(status().isNoContent())
    //             .andDo(print());
    // }
    

    // @BeforeEach
    // @Transactional
    // public void setUp() {
    //     employeesRepository.deleteAll();
    //     userRepository.deleteByUserName("TEST123");
    //     objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    // }

    // User createUser(String test){
    //     ProfileRequestDto profil = new ProfileRequestDto();
    //     profil.setUserName(test);
    //     profil.setUserPassword(test);
    //     profil.setUserEmail(test);
    //     profil.setUserNpwp(test);
    //     profil.setUserNationalId(test);
    //     profil.setUserFullName(test);

    //     return userService.createUser(profil);
    // }

    // Employees create(){
    //     EmployeesRequestDto employeesRequestDto = new EmployeesRequestDto();
    //     UserPhoneRequestDto userPhoneDto = new UserPhoneRequestDto();
    //     userPhoneDto.setUsphPhoneNumber("1223321");
    //     employeesRequestDto.setEmpName("babibu");
    //     employeesRequestDto.setEmail("bab@gmail.com");
    //     employeesRequestDto.setEmpPhone(userPhoneDto);

    //     Employees employees = employeesService.createEmployee(employeesRequestDto);
    //     return employeesService.save(employees);
    
    // }

    // @Test     
    // void whenDelete_thenSucces() throws Exception {
    //     Employees employees = create();

    //     mockMvc.perform(
    //             delete("/employees/"+employees.getEmpEntityid())
    //     ).andExpectAll(
    //             status().is(204)
    //     );

    // }

}
